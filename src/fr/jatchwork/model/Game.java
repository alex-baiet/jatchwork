package fr.jatchwork.model;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Game {
  public static final int PLAYER_COUNT = 2;
  public static final int PATCH_AVAILABLE = 3;
  
  private static Game instance = null;;
  
  /**
   * Delete the old game if it exist and create a new one, accessible with instance().
   */
  public static void newGame() { instance = new Game(); }
  
  /**
   * The only instance of the game.
   */
  public static Game instance() { return instance; }

  /**
   * Generate all patchs for phase 1 only.
   * There is 2 type of patch, each with 20 duplicates.
   * @return
   */
  private static ArrayDeque<Patch> generatePatchs() {
    // Init values
    var patchs = new ArrayDeque<Patch>();
    var patch1 = new Patch(4, 3, 1, """
        ##
        ##
        """);
    var patch2 = new Patch(2, 2, 0, """
        ##
        ##
        """);
    int remains1 = 20;
    int remains2 = 20;
    var rand = new Random();
    
    // Shuffle patchs in the deque
    while (remains1 > 0 && remains2 > 0) {
      var value = rand.nextInt(remains1 + remains2);
      if (value < remains1) {
        patchs.add(patch1);
        remains1--;
      } else {
        patchs.add(patch2);
        remains2--;        
      }
    }
    return patchs;
  }
  
  private final Player[] players;
  private final TimeBoard timeBoard;
  private final ArrayDeque<Patch> patchs;
  
  private Game() {
    players = new Player[] {
        new Player(7, 5),
        new Player(7, 5)
    };
    timeBoard = new TimeBoard(54, new int[] { 5, 11, 17, 23, 29, 35, 41, 47, 53 });
    // Generate patchs
    // TODO: all phase have different implementation of generating patchs, find a solution better than hard-coding
    patchs = generatePatchs();
  }
  
  /**
   * Get a player from the game.
   * The number of available player is PLAYER_COUNT.
   * @param i number of the player
   * @return The wanted player
   */
  public Player player(int i) { return players[i]; }
  
  /**
   * Get the time board of the game.
   */
  public TimeBoard timeBoard() { return timeBoard; }
  
  /**
   * All available patchs in order.
   */
  public Collection<Patch> patchs() {
    return Collections.unmodifiableCollection(patchs);
  }
  
  /**
   * Move the pawn and take (remove) a patch from the available patchs.
   * @param index Index of the patchs to take, must be < to PATCH_AVAILABLE
   * @return Wanted patch
   */
  public Patch buyPatch(int index) {
    if (index < 0 || index >= PATCH_AVAILABLE) {
      throw new IllegalArgumentException("You must choose a patch numero < Game.PATCH_AVAILABLE");
    }
    
    // Simulate the patch pawn deplacement
    for (int i = 0; i < index; i++) {
      patchs.addLast(patchs.removeFirst());
    }
    
    // Get the choosen patch
    return patchs.removeFirst();
  }
  
  /**
   * Better string format for all patchs than the default Collection.toString().
   */
  public String patchsToString() {
    var builder = new StringBuilder();
    for (var patch : patchs) {
      builder.append(patch).append('\n');
    }
    return builder.toString();
  }
  
  public static void main(String[] args) {
    // Test
    System.out.println(instance());
    newGame();
    System.out.println(instance());
    var game = instance();
    System.out.println(game.player(0));
    System.out.println(game.player(1));
    System.out.println(game.timeBoard());
    System.out.println(game.patchsToString());
    System.out.println(game.buyPatch(2));
    System.out.println(game.patchsToString());
  }
}
