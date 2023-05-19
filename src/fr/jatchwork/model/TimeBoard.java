package fr.jatchwork.model;

import java.util.Objects;

/**
 * Board representing the time, on which players move their pawns.
 */
public final class TimeBoard {
  private final boolean[] incomes;
  private final boolean[] leathers;

  /**
   * Create a new TimeBoard
   * @param size
   * @param incomesPos
   */
  public TimeBoard(int size, int[] incomesPos, int[] leatherPatchPos) {
    Objects.requireNonNull(size);
    Objects.requireNonNull(incomesPos);
    Objects.requireNonNull(leatherPatchPos);

    incomes = new boolean[size];
    leathers = new boolean[size];
    
    // Add button incomes on the board
    for (int pos : incomesPos) {
      incomes[pos] = true;
    }
    
    // Add leather patches on the board
    for (int pos : leatherPatchPos) {
      leathers[pos] = true;
    }
  }

  /**
   * Size of the board.
   */
  public int size() { return incomes.length; }

  /**
   * Count the number of button income on the board in a given movement.
   * @param start Where the movement start
   * @param end Where the movement end
   * @return Number of button income found
   */
  public int containsIncome(int start, int end) {
    int count = 0;
    for (int i = start+1; i <= end && i < size(); i++) {
      if (incomes[i]) count++;
    }
    return count;
  }
  
  /**
   * Get leathers patches between the two position, and remove them from the board.
   * @param start Where the movement start
   * @param end Where the movement end
   * @return Number of leathers found
   */
  public int getLeathers(int start, int end) {
    int count = 0;
    for (int i = start+1; i <= end && i < size(); i++) {
      if (leathers[i]) {
        count++;
        leathers[i] = false;
      }
    }
    return count;
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      if (incomes[i]) builder.append('o');
      else if (leathers[i]) builder.append('#');
      else builder.append('=');
    }
    return builder.toString();
  }
}
