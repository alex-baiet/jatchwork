package fr.jatchwork.view;

import fr.jatchwork.model.Game;

/**
 * Display game on console.
 */
public final class ViewConsole {
  /**
   * Display the game.
   */
  public static void displayGame() {
    System.out.println(Game.instance());
  }

  /**
   * Display the list of all available patches.
   */
  public static void displayPatches() {
    System.out.println(Game.instance().patchesToString());
  }

  /**
   * Display a menu with a list of actions.
   */
  public static void displayMenu() {
    System.out.println("\n-- ACTIONS --\n");
    System.out.println("Player " + Game.instance().playing().numero() + " turn");
    System.out.println("Choose an action :");
    System.out.println("- b (board) : Show current state of board game");
    System.out.println("- p (patches) : Show list of available patches");
    System.out.println("- 1, 2, 3 : Buy and place patch corresponding to number");
    System.out.println("- e (end) : End the turn of current player");
    System.out.println("- q (quit) : Stop the game and return to console");
  }

  /**
   * Display the final scores and the winner.
   */
  public static void displayScores() {
    Game game = Game.instance();
    System.out.println("\n-- RESULTS --\n");
    System.out.println("The game is finished.\n");
    System.out.println("Player 1 : " + game.player(0).score() + " points");
    System.out.println("Player 2 : " + game.player(1).score() + " points\n");
    if (game.player(0).score() > game.player(1).score()) {
      System.out.println("Player 1 win !");
    } else if (game.player(0).score() < game.player(1).score()) {
      System.out.println("Player 2 win !");
    } else {
      System.out.println("Draw ! no winner.");
    }
  }
}
