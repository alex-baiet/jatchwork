package fr.jatchwork.model;

/**
 * Board representing the time, on which players move their pawns.
 */
public class TimeBoard {
  private final boolean[] incomes;

  /**
   * Create a new TimeBoard
   * @param size
   * @param incomesPositions
   */
  public TimeBoard(int size, int[] incomesPositions) {
    incomes = new boolean[size];
    
    // Add button incomes on the board
    for (int pos : incomesPositions) {
      incomes[pos] = true;
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
  
  @Override
  public String toString() {
    var builder = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      builder.append(incomes[i] ? 'o' : '=');
    }
    return builder.toString();
  }
  
  public static void main(String[] args) {
    // Test
    var board = new TimeBoard(54, new int[] { 5, 11, 17, 23, 29, 35, 41, 47, 53 });
    
    System.out.println(board);
    
    System.out.println("Income count between 30 and 33 : " + board.containsIncome(30, 33));
    System.out.println("Income count between 5 and 15 : " + board.containsIncome(5, 15));
    System.out.println("Income count between 34 and 41 : " + board.containsIncome(34, 41));
  }
}
