package fr.jatchwork.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Used to place patch for a player.
 * Also compute button income and score.
 */
public final class QuiltBoard {
  /** Default size of a quilt board. */
  public static final int SIZE = 9;
  private static final int BONUS_SIZE = 7;
  
  /** How is currently filled the board. */
  private final boolean[][] board;
  private int remainingSpace;
  private int buttonIncome;
  private final ArrayList<PatchCoord> patches = new ArrayList<>();

  /**
   * Create a new quilt board.
   * @param size Number of column and line
   */
  public QuiltBoard(int size) {
    board = new boolean[size][size];
    remainingSpace = size * size;
  }

  /**
   * Width and height of the quilt board.
   * @return Size of the board
   */
  public int size() { return board.length; }
  
  /**
   * How many empty squares remains
   * @return Total empty squares
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
   * @return Total income
   */
  public int buttonIncome() {
    return buttonIncome;
  }

  /**
   * Used to draw the top and bottom line of the quilt board.
   * @return Generated String
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
   * @return True if insertion is possible, false else.
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
   * @return Coordinate containing the position where the patch can be inserted. Return null if no space where found.
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

  /**
   * Return true if the quilt board is eligible to 7x7 bonus.
   * @return True if eligible, false otherwise
   */
  public boolean fillBonus() {
    for (int x = 0; x <= size() - BONUS_SIZE; x++) {
      for (int y = 0; y <= size() - BONUS_SIZE; y++) {
        if (fillBonus(x, y)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Return true if the quilt board is eligible to 7x7 bonus at a specified position
   * @return True if eligible, false otherwise
   */
  private boolean fillBonus(int x, int y) {
    for (int xx = 0; xx < BONUS_SIZE; xx++) {
      for (int yy = 0; yy < BONUS_SIZE; yy++) {
        if (!board[x + xx][y + yy]) return false;
      }
    }
    return true;
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
   * @return boolean
   */
  private boolean isInside(int x, int y) {
    return x < board.length
        && y < board[x].length
        && x >= 0
        && y >= 0;
  }
}
