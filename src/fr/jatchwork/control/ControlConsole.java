package fr.jatchwork.control;

import java.util.Scanner;

import fr.jatchwork.model.Game;
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
  public static int chooseVersion() {
    while (true) {
      System.out.println("Which version of the game do you want to play ?");
      System.out.println("1. Base version");
      System.out.println("2. Full version");
      System.out.println("3. Graphical version");
      //System.out.println("4. Custom version");
      int version = scanner.nextInt();
      if (version >=1 && version <= 3) return version;
    }
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
      Player player = Game.instance().playing();
      int toBuy = Integer.parseInt(input);
      if (!player.canBuyPatch(toBuy)) {
        System.out.printf(
            "You can't buy the selected patch, you need %d more button(s) to afford it\n",
            Game.instance().getPatch(toBuy).buttonCost() - player.buttonCount());
        break;
      }
      if (!player.canPlacePatch(toBuy)) {
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
    ViewConsole.displayMenu();
  }

  private ControlConsole() { }
}
