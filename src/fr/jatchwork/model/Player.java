package fr.jatchwork.model;

public class Player {  
  private final int num;
  private final QuiltBoard board;
  private int buttonCount;
  private int position = 0;
  private boolean bonusFull = false;
  
  public Player(int num, int quiltBoardSize, int buttonCount) {
    this.num = num;
    board = new QuiltBoard(quiltBoardSize);
    this.buttonCount = buttonCount;
  }
  
  public int numero() { return num; }
  
  public int position() { return position; }
  
  public int buttonCount() { return buttonCount; }
  
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
   * @param numPatch
   */
  public boolean canBuyPatch(int numPatch) {
    return Game.instance().getPatch(numPatch).buttonCost() <= buttonCount;
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
  
  public void buyPatch(int numPatch) {
    Patch patch = Game.instance().getPatch(numPatch);
    Coord coord = board.findSpace(patch);
    if (coord == null) throw new RuntimeException("No place available to place patch.");
    buyPatch(numPatch, coord.x(), coord.y());
  }
  
  private void addPatch(Patch patch, int x, int y) {
    board.add(patch, x, y);
    if (board.remainingSpace() == 0) {
      bonusFull = Game.instance().getBonusFull();
    }
  }
  
  public int score() {
    return buttonCount - board.remainingSpace() * 2 + (bonusFull ? 7 : 0);
  }
  
  @Override
  public String toString() {
    var b = new StringBuilder();
    b.append("-- PLAYER ").append(num).append(" --\n");
    
    var lines = board.toString().split("\n");
    for (int i = 0; i < lines.length || i < 5; i++) {
      if (i < lines.length) b.append(lines[i]);
      if (i == 2) b.append("\tbuttons : ").append(buttonCount);
      if (i == 3) b.append("\tbuttons income : ").append(board.buttonIncome());
      if (i == 4 && bonusFull) b.append("\tBonus full quilt board : 7 points");
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
      Coord pos = board.findSpace(Patch.LEATHER);
      board.add(Patch.LEATHER, pos.x(), pos.y());
    }
    
    if (position + tileCount >= timeBoard.size()) {
      tileCount -= timeBoard.size() - position - 1;
    }
    position += tileCount;
    return tileCount;
  }
  
  public static void main(String[] args) {
    Game.initGame(1);
    Game game = Game.instance();
    Player p = game.playing();
    for (int x = 0; x < p.board.size(); x++) {
      for (int y = 0; y < p.board.size(); y++) {
        if (x == 0 && y == 0) continue;
        p.addPatch(Patch.LEATHER, x, y);
      }
    }
    System.out.println(p);
    System.out.println("Score : " + p.score());
    p.addPatch(Patch.LEATHER, 0, 0);
    System.out.println(p);
    System.out.println("Score : " + p.score());
  }
}
