package fr.jatchwork;

import java.awt.Color;

import fr.jatchwork.control.ControlConsole;
import fr.jatchwork.control.ControlWindow;
import fr.jatchwork.model.Game;
import fr.jatchwork.view.ViewConsole;
import fr.jatchwork.view.ViewWindow;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;

/**
 * Contains the entry of the program.
 */
public final class Main {
  /**
   * The function called when starting the program.
   * @param args List of command line arguments. Not used.
   */
  public static void main(String[] args) {
    // Choose game version
    int version = ControlConsole.initVersion();

    switch (version) {
    case 1, 2 -> {
      // Console version
      ViewConsole.displayGame();
      ViewConsole.displayMenu();

      // Main game loop
      while (!Game.instance().finished()) {
        ControlConsole.manageInput();
      }
      
      ViewConsole.displayGame();
      ViewConsole.displayScores();
    }
    case 3, 4 -> {
      // Graphical version
      Application.run(Color.BLACK, Main::run);
    }
    default ->
      throw new IllegalArgumentException("Unexpected value: " + version);
    }
  }
  
  /**
   * Executed to run the application in a window.
   * @param context Window's context
   */
  private static void run(ApplicationContext context) {
    ViewWindow.setScreenInfo(context.getScreenInfo());

    while (true) {
      context.renderFrame(ViewWindow::displayAll);
      ControlWindow.manageInput(context);
    }
  }

  private Main() { }
}
