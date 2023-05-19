package fr.jatchwork.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class QuiltBoard {
  /** How is currently filled the board. */
  private final boolean[][] board;
  private int remainingSpace;
  private int buttonIncome;
  private final ArrayList<PatchCoord> patches = new ArrayList<>();
  
  public QuiltBoard(int size) {
    board = new boolean[size][size];
    remainingSpace = size * size;
  }

  /**
   * Width and height of the quilt board.
   */
  public int size() { return board.length; }
  
  /**
   * How many empty tiles remains
   */
  public int remainingSpace() { return remainingSpace; }
  
  /**
   * List of all patches inside the quilt board.
   * @return Copy of list of patches
   */
  public List<PatchCoord> patches() { return List.copyOf(patches); }

  /**
   * Add a patch to the quilt board.
   * @param patch What to add
   * @param x Where horizontally
   * @param y Where vertically
   */
  public void add(Patch patch, int x, int y) {
    Objects.requireNonNull(patch);
    if (!fit(patch, x, y)) throw new RuntimeException("The patch did not fit in the quilt board.");
    patches.add(new PatchCoord(patch, new Vector(x, y)));
    buttonIncome += patch.buttonIncome();
    for (int xp = 0; xp < patch.width(); xp++) {
      for (int yp = 0; yp < patch.height(); yp++) {
        if (!patch.getTile(xp, yp)) continue;
        board[x + xp][y + yp] = true;
        remainingSpace--;
      }
    }
  }

  /**
   * Number of button earned per button income case.
   * @return
   */
  public int buttonIncome() {
    return buttonIncome;
  }

  /**
   * Used to draw the top and bottom line of the quilt board.
   */
  private String line() {
    var builder = new StringBuilder();
    builder.append('|');
    for (int i = 0; i < board.length; i++) {
      builder.append('=');
    }
    builder.append("|\n");
    return builder.toString();
  }

  /**
   * Check that the patch can be inserted at given position.
   * @param patch What to test
   * @param x Where horizontally
   * @param y Where vertically
   * @return True if insertion is possibl, false else.
   */
  public boolean fit(Patch patch, int x, int y) {
    Objects.requireNonNull(patch);
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

  /**
   * Get a coordinate where patch can be inserted.
   * @param patch What to place
   * @return Coord containing the position where the patch can be inserted. Return null if no space where found.
   */
  public Vector findSpace(Patch patch) {
    Objects.requireNonNull(patch);
    for (int x = 0; x < size(); x++) {
      for (int y = 0; y < size(); y++) {
        if (fit(patch, x, y)) return new Vector(x, y);
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
   * True if position is inside the quilt board.
   */
  private boolean isInside(int x, int y) {
    return x < board.length
        && y < board[x].length
        && x >= 0
        && y >= 0;
  }
}
