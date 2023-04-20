package fr.jatchwork.model;

public class Player {
  private final QuiltBoard board;
  private int buttonCount;
  private int position = 0;
  
  public Player(int quiltBoardSize, int buttonCount) {
    board = new QuiltBoard(quiltBoardSize);
    this.buttonCount = buttonCount;
  }
}
