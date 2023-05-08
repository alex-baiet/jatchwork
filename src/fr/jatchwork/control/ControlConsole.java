package fr.jatchwork.control;

import java.util.Scanner;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Player;
import fr.jatchwork.view.ViewConsole;

public class ControlConsole {
  private static final Scanner scanner = new Scanner(System.in);
  
  public static int chooseVersion() {
    System.out.println("Which version of the game do you want to play ?");
    System.out.println("1. Base version");
    System.out.println("2. Full version");
    return scanner.nextInt();
  }

  public static void manageInput() {
    String input = scanner.next();
    switch (input) {
    case "b" -> {
      ViewConsole.displayGame();
    }
    case "p" -> {
      ViewConsole.displayPatchs();
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
}
