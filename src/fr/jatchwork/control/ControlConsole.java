package fr.jatchwork.control;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Scanner;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
import fr.jatchwork.model.PatchBuilder;
import fr.jatchwork.model.Player;
import fr.jatchwork.view.ViewConsole;

/**
 * Manage input on the console versions.
 */
public final class ControlConsole {
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Display a dialog and wait for input to choose the version of the game.
   * @return Choosen version.
   */
  public static int initVersion() {
    // Ask version
    int version = 0;
    while (version < 1 || version > 4) {
      System.out.println("Which version of the game do you want to play ?");
      System.out.println("1. Base version");
      System.out.println("2. Full version");
      System.out.println("3. Graphical version");
      System.out.println("4. Custom version");
      version = scanner.nextInt();
    }

    if (version == 4) {
      while (Game.instance() == null) {
        // Ask patches file
        System.out.println("This version require a file of patches.");
        System.out.println("Specify the path of this file :");
        final var path = Path.of(scanner.next());
        try {
          Game.initGame(version, PatchBuilder.fromFile(path));
        } catch (NoSuchFileException e) {
          System.out.printf("The file %s does not exist or is not a file.\n", e.getMessage());
        } catch (IOException e) {
          System.out.println(e.getMessage());
        } catch (Exception e) {
          System.out.println("Malformed file. Please fix the file or specify another one.");
        }
      }
    } else {
      Game.initGame(version);
    }

    return version;
  }

  /**
   * Wait for input for the main game loop.
   */
  public static void manageInput() {
    String input = scanner.next();
    switch (input) {
    case "b" -> {
      ViewConsole.displayGame();
    }
    case "p" -> {
      ViewConsole.displayPatches();
    }
    case "0", "1", "2" -> {
      final Game game = Game.instance();
      final Player player = game.playing();
      int toBuy = Integer.parseInt(input);
      if (game.patchCount() <= toBuy) {
        System.out.println("There is not enough patch to buy the selected one.\n");
        break;
      }
      final Patch patch = game.getPatch(toBuy);
      if (!player.canBuyPatch(patch)) {
        System.out.printf(
            "You can't buy the selected patch, you need %d more button(s) to afford it\n\n",
            patch.buttonCost() - player.buttonCount());
        break;
      }
      if (!player.canPlacePatch(patch)) {
        System.out.println("There is not enough space in the quiltboard to place the patch\n");
        break;
      }
      player.buyPatch(toBuy);
    }
    case "e" -> {
      Game.instance().playing().endTurn();
    }
    case "q" -> {
      System.exit(0);
    }
    default -> {
      System.out.println("Please enter a choice listed below :\n");
    }
    };

    // Place leather patches
    final var player = Game.instance().playing();
    while (player.leatherCount() > 0) {
      player.buyPatch(Patch.LEATHER);
      player.removeLeather();
    }

    ViewConsole.displayMenu();
  }

  private ControlConsole() { }
}
