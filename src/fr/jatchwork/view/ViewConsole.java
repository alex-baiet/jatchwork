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
   * Display the list of all available patchs.
   */
  public static void displayPatchs() {
    System.out.println(Game.instance().patchsToString());
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

  /** Only for test */
  public static void main(String[] args) {
    Game.instance().player(0).buyPatch(0, 0, 0);
    System.out.println(Game.instance().player(0).position());
    displayGame();
    System.out.println("############################################################");
    displayPatchs();
    System.out.println("############################################################");
    displayMenu();
  }
}
