package fr.jatchwork.model;

import java.util.ArrayList;

public class QuiltBoard {
  /** How is currently filled the board. */
  private final boolean[][] board;
  private int remainingSpace;
  private int buttonIncome;
  private final ArrayList<PatchCoord> patchs = new ArrayList<>();
  
  public QuiltBoard(int size) {
    board = new boolean[size][size];
    remainingSpace = size * size;
  }
  
  /**
   * How many empty tiles remains
   */
  public int remainingSpace() { return remainingSpace; }
  
  public void add(Patch patch, int x, int y) {
    patchs.add(new PatchCoord(patch, x, y));
  }
  
  public int buttonIncome() {
    return buttonIncome;
  }
  
  private String line() {
    var builder = new StringBuilder();
    builder.append('|');
    for (int i = 0; i < board.length; i++) {
      builder.append('-');
    }
    builder.append("|\n");
    return builder.toString();
  }
  
  public boolean fit(Patch patch, int x, int y) {
    return false;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Quilt board (remaining space : ").append(remainingSpace).append(")\n\n");

    builder.append(line());
    for (int i = 0; i < board.length; i++) {
      builder.append('|');
      for (int j = 0; j < board[i].length; j++) {
        builder.append(board[i][j] ? '#' : ' ');
      }
      builder.append("|\n");
    }
    builder.append(line());

    return builder.toString();
  }
  
  public static void main(String[] args) {
    QuiltBoard board = new QuiltBoard(7);
    System.out.println(board);
  }
}
