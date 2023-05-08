package fr.jatchwork.model;

/**
 * Board representing the time, on which players move their pawns.
 */
public class TimeBoard {
  private final boolean[] incomes;
  private final boolean[] leathers;

  /**
   * Create a new TimeBoard
   * @param size
   * @param incomesPos
   */
  public TimeBoard(int size, int[] incomesPos, int[] leatherPatchPos) {
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
  
  public static void main(String[] args) {
    // Test
    var board = new TimeBoard(54, new int[] { 5, 11, 17, 23, 29, 35, 41, 47, 53 }, new int[] { 20, 26, 32, 44, 50 });
    
    System.out.println(board);
    
    System.out.println("Income count between 30 and 33 : " + board.containsIncome(30, 33));
    System.out.println("Income count between 5 and 15 : " + board.containsIncome(5, 15));
    System.out.println("Income count between 34 and 41 : " + board.containsIncome(34, 41));
  }
}
