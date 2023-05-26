package fr.jatchwork.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Contains all data of the current game.
 */
public final class Game {
  /** Number of player. */
  public static final int PLAYER_COUNT = 2;
  /** Number of patches available to be bought */
  public static final int PATCH_AVAILABLE = 3;

  private static Game instance = null;

  /**
   * The only instance of the game.
   * @return The instance
   */
  public static Game instance() {
    return instance;
  }

  /**
   * Create the instance if it does not exists.
   * @param phase The current phase to initialize the game.
   */
  public static void initGame(int phase) {
    if (instance != null) throw new RuntimeException("An instance of Game already exist.");
    instance = new Game(phase);
  }

  /**
   * Generate all patches for phase 1 only. There is 2 type of patch, each with 20
   * duplicates.
   * @return The list of generated patches
   */
  private static ArrayDeque<Patch> generatePatches1() {
    // Init values
    var patches = new ArrayDeque<Patch>();
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

    // Shuffle patches in the deque
    while (remains1 > 0 && remains2 > 0) {
      var value = rand.nextInt(remains1 + remains2);
      if (value < remains1) {
        patches.add(patch1);
        remains1--;
      } else {
        patches.add(patch2);
        remains2--;
      }
    }
    return patches;
  }

  /**
   * Generate all patches for phase 2 and 3.
   * @return The list of generated patches.
   */
  private static ArrayDeque<Patch> generatePatches2() {
    // Init values
    var patches = new ArrayList<Patch>(33);

    // Patches with 1 button
    patches.add(new Patch(1, 2, 0, """
        ##
        """));
    patches.add(new Patch(2, 2, 0, """
        ###
        """));
    patches.add(new Patch(3, 3, 1, """
        ####
        """));
    patches.add(new Patch(1, 7, 1, """
        #####
        """));
    patches.add(new Patch(3, 1, 0, """
        #.
        ##
        """));
    patches.add(new Patch(1, 3, 0, """
        #.
        ##
        """));
    patches.add(new Patch(2, 3, 1, """
        .##
        ##.
        """));
    patches.add(new Patch(6, 7, 3, """
        ##.
        .##
        """));
    patches.add(new Patch(2, 4, 1, """
        ..#
        ###
        """));
    patches.add(new Patch(6, 4, 2, """
        ..#
        ###
        """));
    patches.add(new Patch(5, 6, 2, """
        ##
        ##
        """));
    patches.add(new Patch(2, 2, 0, """
        .#.
        ###
        """));
    patches.add(new Patch(2, 1, 0, """
        #.#
        ###
        """));
    patches.add(new Patch(3, 2, 1, """
        .###
        ##..
        """));
    patches.add(new Patch(3, 10, 2, """
        ...#
        ####
        """));
    patches.add(new Patch(2, 2, 0, """
        ##.
        ###
        """));
    patches.add(new Patch(4, 3, 1, """
        ..#.
        ####
        """));
    patches.add(new Patch(4, 7, 2, """
        .##.
        ####
        """));
    patches.add(new Patch(5, 1, 1, """
        #.#
        ###
        """));
    patches.add(new Patch(5, 10, 3, """
        ##..
        ####
        """));
    patches.add(new Patch(2, 4, 0, """
        ###.
        .###
        """));
    patches.add(new Patch(6, 3, 2, """
        .#.
        ###
        #.#
        """));
    patches.add(new Patch(4, 5, 2, """
        .#.
        ###
        .#.
        """));
    patches.add(new Patch(3, 0, 1, """
        ..#.
        ####
        ..#.
        """));
    patches.add(new Patch(1, 2, 0, """
        ..#.
        ####
          .#..
        """));
    patches.add(new Patch(3, 2, 0, """
        #.#
        ###
        #.#
        """));
    patches.add(new Patch(4, 10, 3, """
        ..#
        .##
        ##.
        """));
    patches.add(new Patch(3, 5, 1, """
        .##.
        ####
        .##.
        """));
    patches.add(new Patch(5, 5, 2, """
        .#.
        .#.
        ###
        """));
    patches.add(new Patch(2, 1, 0, """
        ...#
        ####
        #...
        """));
    patches.add(new Patch(4, 1, 1, """
        ..#..
        #####
        ..#..
        """));
    patches.add(new Patch(6, 8, 3, """
        ##.
        ##.
        .##
        """));
    patches.add(new Patch(2, 7, 2, """
        #...
        ####
        #...
        """));
    
    Collections.shuffle(patches);
    return new ArrayDeque<>(patches);
  }

  private final Player[] players;
  private Player playing;
  private final TimeBoard timeBoard;
  private ArrayDeque<Patch> patches;
  private boolean bonusFullGiven = false;

  /**
   * Create a new game data.
   * @param phase Version chosen by the user
   */
  private Game(int phase) {
    players = new Player[] { new Player(1, QuiltBoard.SIZE, 5), new Player(2, QuiltBoard.SIZE, 5) };
    playing = players[0];
    timeBoard = new TimeBoard(54,
        new int[] { 5, 11, 17, 23, 29, 35, 41, 47, 53 },
        new int[] { 20, 26, 32, 44, 50 });
    switch (phase) {
    case 1 -> patches = generatePatches1();
    case 2, 3 -> patches = generatePatches2();
    default -> throw new IllegalArgumentException("Unexpected phase: " + phase);
    }
  }

  /**
   * Get a player from the game. The number of available player is PLAYER_COUNT.
   * 
   * @param i number of the player
   * @return The wanted player
   */
  public Player player(int i) {
    return players[i];
  }
  
  /**
   * State of the game.
   * @return True if both players can't play, false otherwise.
   */
  public boolean finished() {
    for (Player player : players) {
      if (player.position() < timeBoard.size()-1 || player.leatherCount() > 0) return false;
    }
    return true;
  }
  
  /**
   * Get the player with the best score.
   * @return The player currently winning the game
   */
  public Player winningPlayer() {
    return Stream.of(players)
        .max((p1, p2) -> Integer.compare(p1.score(), p2.score()))
        .get();
  }

  /**
   * Player which is the turn.
   * @return Player
   */
  public Player playing() {
    Player opponent = players[0] == playing ? players[1] : players[0];
    if (playing.leatherCount() > 0) return playing;
    if (opponent.position() < playing.position()) {
      playing = opponent;
    }
    return playing;
  }

  /**
   * Get the time board of the game.
   * @return TimeBoard
   */
  public TimeBoard timeBoard() {
    return timeBoard;
  }

  /**
   * All available patches in order.
   * @return Unmodifiable collection of patches
   */
  public Collection<Patch> patches() {
    return Collections.unmodifiableCollection(patches);
  }

  /**
   * Move the pawn and take (remove) a patch from the available patches.
   * @param index Index of the patches to take, must be superior to PATCH_AVAILABLE
   * @return Wanted patch
   */
  public Patch buyPatch(int index) {
    if (index < 0 || index >= PATCH_AVAILABLE) {
      throw new IllegalArgumentException("You must choose a patch numero < Game.PATCH_AVAILABLE");
    }

    // Simulate the patch pawn deplacement
    for (int i = 0; i < index; i++) {
      patches.addLast(patches.removeFirst());
    }

    // Get the choosen patch
    return patches.removeFirst();
  }

  /**
   * Get the patch at index from the neutral pawn
   * @param index Index of the patch
   * @return Patch at given index
   */
  public Patch getPatch(int index) {
    int i = 0;
    for (var patch : patches) {
      if (i++ == index)
        return patch;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }
  
  /**
   * The number of remaining patch to bought in the game.
   * @return Number of remaining patch
   */
  public int patchCount() {
    return patches.size();
  }
  
  /**
   * Get the bonus for completing a quilt board.
   * @return True if no player already took it, false otherwise.
   */
  public boolean getBonusFull() {
    if (!bonusFullGiven) {
      bonusFullGiven = true;
      return true;
    }
    return false;
  }

  /**
   * Better string format for all patches than the default Collection.toString().
   * @return Generated string
   */
  public String patchesToString() {
    var builder = new StringBuilder();
    int i = 0;
    for (var patch : patches) {
      if (i == PATCH_AVAILABLE) builder.insert(0, "(inaccessible)\n\n");
      builder.insert(0, '\n').insert(0, patch);
      builder.insert(0, '\n').insert(0, i).insert(0, "n°");
      i++;
    }
    return builder.substring(0, builder.length()-2);
  }

  @Override
  public String toString() {
    var b = new StringBuilder("-- GAME --\n\n");
    b.append(" ".repeat(players[0].position())).append("|p1\n");
    b.append(timeBoard).append('\n');
    b.append(" ".repeat(players[1].position())).append("|p2\n");
    b.append("*o are button incomes\n");
    b.append("*# are leather patches\n\n");
    b.append(players[0]).append("\n\n");
    b.append(players[1]);
    return b.toString();
  }
}
