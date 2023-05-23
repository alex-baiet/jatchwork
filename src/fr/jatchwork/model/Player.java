package fr.jatchwork.model;

import java.util.Objects;

/**
 * A player and all his data.
 */
public final class Player {  
  private final int num;
  private final QuiltBoard board;
  private int buttonCount;
  private int position = 0;
  private boolean bonus = false;
  
  /**
   * Create a new player
   * @param num His number, only used for display
   * @param quiltBoardSize Width and height of his quilt board
   * @param buttonCount Number of starting buttons
   */
  public Player(int num, int quiltBoardSize, int buttonCount) {
    this.num = num;
    board = new QuiltBoard(quiltBoardSize);
    this.buttonCount = buttonCount;
  }
  
  /**
   * Number of the player, used only for display. This is not an identifier.
   * @return Numero
   */
  public int numero() { return num; }
  
  /**
   * Current position on the time board.
   * @return Current position
   */
  public int position() { return position; }

  /**
   * Current number of button the player has.
   * @return Total count
   */
  public int buttonCount() { return buttonCount; }
  
  /**
   * Number of button gained at each "button income" of the time board.
   * @return Button income
   */
  public int buttonIncome() { return board.buttonIncome(); }

  /**
   * Does the player has the 7x7 bonus.
   * @return True or false
   */
  public boolean hasBonus() { return bonus; }
  
  /**
   * End the turn of a player.
   */
  public void endTurn() {
    var game = Game.instance();
    Player player;
    int max = 0;
    // Get the more advanced player
    for (int i = 0; i < Game.PLAYER_COUNT; i++) {
      player = game.player(i);
      if (player == this) continue;
      if (player.position > max) max = player.position;
    }
    // Move player
    if (max >= position) {
      int moveCount = max - position + 1;
      moveCount = move(moveCount);
      buttonCount += moveCount;
    }
  }
  
  /**
   * Return true if the player has enough buttons to buy the selected patch.
   * @param numPatch Patch to buy
   * @return True if it can be bought
   */
  public boolean canBuyPatch(int numPatch) {
    return Game.instance().getPatch(numPatch).buttonCost() <= buttonCount;
  }
  
  /**
   * Return true if the patch can be placed somewhere on the quilt board.
   * @param numPatch Number of the patch to test
   * @return True if there is available space, false otherwise.
   */
  public boolean canPlacePatch(int numPatch) {
    return board.findSpace(Game.instance().getPatch(numPatch)) != null;
  }

  /**
   * Buy a patch and place it the the player's quilt board.
   * @param numPatch Numero of the patch to bought.
   * @param x X position in the quiltboard.
   * @param y Y position in the quiltboard.
   */
  public void buyPatch(int numPatch, int x, int y) {
    Game game = Game.instance();
    // Buy the patch
    Patch patch = game.buyPatch(numPatch);

    // Place on the quiltboard
    addPatch(patch, x, y);

    buttonCount -= patch.buttonCost();
    if (buttonCount < 0) throw new RuntimeException("Player bought an overpriced patch.");
    move(patch.timeCost());
  }

  /**
   * Buy a patch and place it automatically on the quiltboard.
   * @param numPatch Patch to buy.
   */
  public void buyPatch(int numPatch) {
    Patch patch = Game.instance().getPatch(numPatch);
    Vector coord = board.findSpace(patch);
    if (coord == null) throw new RuntimeException("No place available to place patch.");
    buyPatch(numPatch, coord.x(), coord.y());
  }

  /**
   * Add a patch to the quilt board.
   * @param patch To add to the quilt board
   * @param x Where horizontally
   * @param y Where vertically
   */
  private void addPatch(Patch patch, int x, int y) {
    Objects.requireNonNull(patch);
    board.add(patch, x, y);
    if (board.fillBonus()) {
      bonus = Game.instance().getBonusFull();
    }
  }

  /**
   * The current score of the player.
   * @return The player's score
   */
  public int score() {
    return buttonCount - board.remainingSpace() * 2 + (bonus ? 7 : 0);
  }

  /**
   * The quilt board of the player.
   * @return Actual quilt board
   */
  public QuiltBoard board() { return board; }

  @Override
  public String toString() {
    var b = new StringBuilder();
    b.append("-- PLAYER ").append(num).append(" --\n");
    
    var lines = board.toString().split("\n");
    for (int i = 0; i < lines.length || i < 5; i++) {
      if (i < lines.length) b.append(lines[i]);
      if (i == 2) b.append("\tbuttons : ").append(buttonCount);
      if (i == 3) b.append("\tbuttons income : ").append(board.buttonIncome());
      if (i == 4 && bonus) b.append("\tBonus full quilt board : 7 points");
      b.append('\n');
    }
    return b.substring(0, b.length() - 1);
  }

  /**
   * Move the player on the time board.
   * @param tileCount
   * @return The real number of movement made by the player.
   */
  private int move(int tileCount) {
    var timeBoard = Game.instance().timeBoard();
    
    // Button incomes
    var incomes = timeBoard.containsIncome(position, position + tileCount);
    buttonCount += incomes * board.buttonIncome();
    
    // Leathers patches
    var leathers = timeBoard.getLeathers(position, position + tileCount);
    for (int i = 0; i < leathers; i++) {
      Vector pos = board.findSpace(Patch.LEATHER);
      addPatch(Patch.LEATHER, pos.x(), pos.y());
    }
    
    if (position + tileCount >= timeBoard.size()) {
      tileCount -= timeBoard.size() - position - 1;
    }
    position += tileCount;
    return tileCount;
  }
}
