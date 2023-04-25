package fr.jatchwork;

import fr.jatchwork.control.ControlConsole;
import fr.jatchwork.view.ViewConsole;

public class Main {

  public static void main(String[] args) {
    ViewConsole.displayGame();
    ViewConsole.displayMenu();
    while (true) {
      ControlConsole.manageInput();
    }
  }

}
