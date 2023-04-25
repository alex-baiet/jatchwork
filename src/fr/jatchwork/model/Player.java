package fr.jatchwork.model;

public class Player {  
  private final int num;
  private final QuiltBoard board;
  private int buttonCount;
  private int position = 0;
  
  public Player(int num, int quiltBoardSize, int buttonCount) {
    this.num = num;
    board = new QuiltBoard(quiltBoardSize);
    this.buttonCount = buttonCount;
  }
  
  public int numero() { return num; }
  
  public int position() { return position; }
  
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
      move(max - position + 1);
    }
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
    buttonCount -= patch.buttonCost();
    if (buttonCount < 0) throw new RuntimeException("Player bought an overpriced patch.");
    move(patch.timeCost());
    
    // Place on the quiltboard
    board.add(patch, x, y);
  }
  
  public void buyPatch(int numPatch) {
    Patch patch = Game.instance().getPatch(numPatch);
    Coord coord = board.findSpace(patch);
    if (coord == null) throw new RuntimeException("No place available to place patch.");
    buyPatch(numPatch, coord.x(), coord.y());
  }
  
  public int score() {
    return buttonCount - board.remainingSpace() * 2;
  }
  
  @Override
  public String toString() {
    var b = new StringBuilder();
    b.append("-- PLAYER ").append(num).append(" --\n\n");
    b.append("buttons : ").append(buttonCount).append('\n');
    b.append("buttons income : ").append(board.buttonIncome()).append("\n");
    b.append(board);
    return b.toString();
  }

  /**
   * Move the player on the time board.
   * @param tileCount
   */
  private void move(int tileCount) {
    var timeBoard = Game.instance().timeBoard();
    var incomes = timeBoard.containsIncome(position, position + tileCount);
    buttonCount += incomes * board.buttonIncome();
    position += tileCount;
    if (position >= timeBoard.size()) position = timeBoard.size() - 1;
  }
  
  public static void main(String[] args) {
    Game game = Game.instance();
    game.player(1).move(10);
    Player player = game.player(0);
    player.buttonCount = 20;
    System.out.println(player);
    player.buyPatch(2, 0, 0);
    System.out.println(player);
    player.buyPatch(0, 2, 0);
    System.out.println(player);
    player.endTurn();
    System.out.println(player);
    System.out.println("Player score : " + player.score());
  }
}
