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
  
  public int size() { return board.length; }
  
  /**
   * How many empty tiles remains
   */
  public int remainingSpace() { return remainingSpace; }
  
  public void add(Patch patch, int x, int y) {
    if (!fit(patch, x, y)) throw new RuntimeException("The patch did not fit in the quilt board.");
    patchs.add(new PatchCoord(patch, x, y));
    buttonIncome += patch.buttonIncome();
    for (int xp = 0; xp < patch.width(); xp++) {
      for (int yp = 0; yp < patch.height(); yp++) {
        if (!patch.getTile(xp, yp)) continue;
        board[x + xp][y + yp] = true;
        remainingSpace--;
      }
    }
  }
  
  public int buttonIncome() {
    return buttonIncome;
  }
  
  private String line() {
    var builder = new StringBuilder();
    builder.append('|');
    for (int i = 0; i < board.length; i++) {
      builder.append('=');
    }
    builder.append("|\n");
    return builder.toString();
  }
  
  public boolean fit(Patch patch, int x, int y) {
    for (int xp = 0; xp < patch.width(); xp++) {
      for (int yp = 0; yp < patch.height(); yp++) {
        if (patch.getTile(xp, yp)
            && (!isInside(x + xp, y + yp) || board[x + xp][y + yp])) {
          return false;
        }
      }
    }
    // All part of the patch fit in the board
    return true;
  }
  
  public Coord findSpace(Patch patch) {
    for (int x = 0; x < size(); x++) {
      for (int y = 0; y < size(); y++) {
        if (fit(patch, x, y)) return new Coord(x, y);
      }
    }
    return null;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Quilt board (remaining space : ").append(remainingSpace).append(")\n");

    builder.append(line());
    for (int y = 0; y < board.length; y++) {
      builder.append('|');
      for (int x = 0; x < board[y].length; x++) {
        builder.append(board[x][y] ? '#' : '.');
      }
      builder.append("|\n");
    }
    builder.append(line());

    return builder.toString();
  }
  
  /**
   * True if position is inside the quiltboard, whatever the content.
   */
  private boolean isInside(int x, int y) {
    return x < board.length
        && y < board[x].length
        && x >= 0
        && y >= 0;
  }
  
  public static void main(String[] args) {
    QuiltBoard board = new QuiltBoard(7);
    System.out.println(board);
    
    var p = new Patch(2, 3, 1, """
        ##.
        .##
        ##.
        """);
    board.add(p, 0, 0);
    
    System.out.println(board);

    var p2 = new Patch(2, 3, 3, """
        .#.
        ###
        .#.
        """);
    board.add(p2, 1, 2);
    
    System.out.println(board);
    System.out.println("buttonIncome : " + board.buttonIncome());
  }
}
