package fr.jatchwork;

import fr.jatchwork.control.ControlConsole;
import fr.jatchwork.model.Game;
import fr.jatchwork.view.ViewConsole;

public class Main {
  /**
   * The function called when starting the program.
   * @param args List of command line arguments. Not used.
   */
  public static void main(String[] args) {
    // Choose game version
    int version = ControlConsole.chooseVersion();
    Game.initGame(version);

    ViewConsole.displayGame();
    ViewConsole.displayMenu();

    // Main game loop
    while (!Game.instance().finished()) {
      ControlConsole.manageInput();
    }
    
    ViewConsole.displayGame();
    ViewConsole.displayScores();
  }

}
