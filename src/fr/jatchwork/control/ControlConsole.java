package fr.jatchwork.control;

import java.util.Scanner;

import fr.jatchwork.model.Game;
import fr.jatchwork.view.ViewConsole;

public class ControlConsole {
  private static final Scanner scanner = new Scanner(System.in);

  public static void manageInput() {
    String input = scanner.next();
    switch (input) {
    case "b" -> {
      ViewConsole.displayGame();
    }
    case "p" -> {
      ViewConsole.displayPatchs();
    }
    case "1", "2", "3" -> {
      Game.instance().playing().buyPatch(Integer.parseInt(input));
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
