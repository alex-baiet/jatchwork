package fr.jatchwork.model;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import fr.jatchwork.control.ControlConsole;

public class Game {
  public static final int PLAYER_COUNT = 2;
  public static final int PATCH_AVAILABLE = 3;

  private static Game instance = null;

  /**
   * The only instance of the game.
   */
  public static Game instance() {
    if (instance == null) {
      instance = new Game();
      instance.askUserForVersion();
    }
    return instance;
  }

  private void askUserForVersion() {
      switch (ControlConsole.chooseVersion()) {
      case 1 -> patchs = generatePatchs();
      case 2 -> patchs = generateAllPatches();
      default -> {
        System.out.println("Invalid choice, defaulting to base version.");
        patchs = generatePatchs();
      }
    }
  }

  /**
   * Generate all patchs for phase 1 only. There is 2 type of patch, each with 20
   * duplicates.
   * 
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

  private static ArrayDeque<Patch> generateAllPatches() {
    // Init values
    var patchs = new ArrayDeque<Patch>();

    // Patchs with 1 button
    patchs.add(new Patch(1, 2, 0, """
        ##
        """));
    patchs.add(new Patch(2, 2, 0, """
        ###
        """));
    patchs.add(new Patch(3, 3, 1, """
        ####
        """));
    patchs.add(new Patch(1, 7, 1, """
        #####
        """));
    patchs.add(new Patch(3, 1, 0, """
        #.
        ##
        """));
    patchs.add(new Patch(1, 3, 0, """
        #.
        ##
        """));
    patchs.add(new Patch(2, 3, 1, """
        .##
        ##.
        """));
    patchs.add(new Patch(6, 7, 3, """
        ##.
        .##
        """));
    patchs.add(new Patch(2, 4, 1, """
        ..#
        ###
        """));
    patchs.add(new Patch(6, 4, 2, """
        ..#
        ###
        """));
    patchs.add(new Patch(5, 6, 2, """
        ##
        ##
        """));
    patchs.add(new Patch(2, 2, 0, """
        .#.
        ###
        """));
    patchs.add(new Patch(2, 1, 0, """
        #.#
        ###
        """));
    patchs.add(new Patch(3, 2, 1, """
        .###
        ##..
        """));
    patchs.add(new Patch(3, 10, 2, """
        ...#
        ####
        """));
    patchs.add(new Patch(2, 2, 0, """
        ##.
        ###
        """));
    patchs.add(new Patch(4, 3, 1, """
        ..#.
        ####
        """));
    patchs.add(new Patch(4, 7, 2, """
        .##.
        ####
        """));
    patchs.add(new Patch(5, 1, 1, """
        #.#
        ###
        """));
    patchs.add(new Patch(5, 10, 3, """
        ##..
        ####
        """));
    patchs.add(new Patch(2, 4, 0, """
        ###.
        .###
        """));
    patchs.add(new Patch(6, 3, 2, """
        .#.
        ###
        #.#
        """));
    patchs.add(new Patch(4, 5, 2, """
        .#.
        ###
        .#.
        """));
    patchs.add(new Patch(3, 0, 1, """
        ..#.
        ####
        ..#.
        """));
    patchs.add(new Patch(1, 2, 0, """
        ..#.
        ####
          .#..
        """));
    patchs.add(new Patch(3, 2, 0, """
        #.#
        ###
        #.#
        """));
    patchs.add(new Patch(4, 10, 3, """
        ..#
        .##
        ##.
        """));
    patchs.add(new Patch(3, 5, 1, """
        .##.
        ####
        .##.
        """));
    patchs.add(new Patch(5, 5, 2, """
        .#.
        .#.
        ###
        """));
    patchs.add(new Patch(2, 1, 0, """
        ...#
        ####
        #...
        """));
    patchs.add(new Patch(4, 1, 1, """
        ..#..
        #####
        ..#..
        """));
    patchs.add(new Patch(6, 8, 3, """
        ##.
        ##.
        .##
        """));
    patchs.add(new Patch(2, 7, 2, """
        #...
        ####
        #...
        """));
    return patchs;
  }

  private final Player[] players;
  private Player playing;
  private final TimeBoard timeBoard;
  private ArrayDeque<Patch> patchs;

  private Game() {
    players = new Player[] { new Player(1, 7, 5), new Player(2, 7, 5) };
    playing = players[0];
    timeBoard = new TimeBoard(54, new int[] { 5, 11, 17, 23, 29, 35, 41, 47, 53 });
    // Generate patchs
    // TODO: all phase have different implementation of generating patchs, find a
    // solution better than hard-coding
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
   * Player which is the turn.
   */
  public Player playing() {
    Player opponent = players[0] == playing ? players[1] : players[0];
    if (opponent.position() < playing.position()) {
      playing = opponent;
    }
    return playing;
  }

  /**
   * Get the time board of the game.
   */
  public TimeBoard timeBoard() {
    return timeBoard;
  }

  /**
   * All available patchs in order.
   */
  public Collection<Patch> patchs() {
    return Collections.unmodifiableCollection(patchs);
  }

  /**
   * Move the pawn and take (remove) a patch from the available patchs.
   * 
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

  public Patch getPatch(int index) {
    int i = 0;
    for (var patch : patchs) {
      if (i++ == index)
        return patch;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  /**
   * Better string format for all patchs than the default Collection.toString().
   */
  public String patchsToString() {
    var builder = new StringBuilder();
    int i = 0;
    for (var patch : patchs) {
      if (i++ == PATCH_AVAILABLE) builder.insert(0, "(inaccessible)\n\n");
      builder.insert(0, '\n').insert(0, patch);
    }
    return builder.substring(0, builder.length()-1);
  }

  @Override
  public String toString() {
    var b = new StringBuilder("-- GAME --\n\n");
    b.append(" ".repeat(players[0].position())).append("|p1\n");
    b.append(timeBoard).append('\n');
    b.append(" ".repeat(players[1].position())).append("|p2\n\n");
    b.append("*o are button incomes\n\n");
    b.append(players[0]);
    b.append(players[1]);
    return b.toString();
  }

  public static void main(String[] args) {
    // Test
    System.out.println(instance());
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
