package fr.jatchwork.control;

import java.util.Arrays;
import java.util.Objects;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
import fr.jatchwork.model.PatchSetter;
import fr.jatchwork.model.QuiltBoard;
import fr.jatchwork.model.Vector;
import fr.jatchwork.view.ViewWindow;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * Manage input on the windowed versions.
 */
public final class ControlWindow {

  // Temporary default patch for test, to delete
  private static PatchSetter selectedPatch = null;

  private static final Button[] patchButtons = new Button[] {
      new Button(() -> selectPatch(0)),
      new Button(() -> selectPatch(1)),
      new Button(() -> selectPatch(2)),
  };

  private static final Button btnEndTurn = new Button(
      () -> {
        selectedPatch = null;
        Game.instance().playing().endTurn();
      },
      null, "End turn", ViewWindow.FONT);

  private static final PlayerButtons[] playerBtns = new PlayerButtons[Game.PLAYER_COUNT];
  
  private static final Button[][][] quiltBoardBtns = new Button[Game.PLAYER_COUNT][QuiltBoard.SIZE][QuiltBoard.SIZE];
  
  static {
    // Buttons player init
    for (int i = 0; i < Game.PLAYER_COUNT; i++) {
      playerBtns[i] = new PlayerButtons(
          new Button(
              () -> { if (selectedPatch != null) selectedPatch.rotate(); },
              null, "Rotate", ViewWindow.FONT),
          new Button(
              () -> { if (selectedPatch != null) selectedPatch.flip(); },
              null, "Flip", ViewWindow.FONT),
          new Button(
              () -> { /*TODO: buy patch*/ },
              null, "Buy patch", ViewWindow.FONT));
    }
    
    // Buttons quilt board init
    for (int i = 0; i < Game.PLAYER_COUNT; i++) {
      final int icopy = i;
      for (int x = 0; x < quiltBoardBtns[i].length; x++) {
        final int xcopy = x;
        for (int y = 0; y < quiltBoardBtns[i][x].length; y++) {
          final int ycopy = y;
          quiltBoardBtns[i][x][y] = new Button(() -> {
            if (Game.instance().playing() != Game.instance().player(icopy)) return;
            if (selectedPatch != null) selectedPatch.setPosition(new Vector(xcopy, ycopy));
          });
        }
      }
    }
  }

  /**
   * The selected patch from the instance of Game.
   * @return Patch. May be null
   */
  public static Patch selectedPatch() { return selectedPatch != null ? selectedPatch.patch() : null; }

  /**
   * Used to place the selected patch on a quilt board.
   * @return The patch setter
   */
  public static PatchSetter patchSetter() { return selectedPatch; }

  public static Button[][] quiltBoardBtns(int i) {
    return quiltBoardBtns[i];
  }
  
  /**
   * Change the selected patch.
   * @param i Index of the new selected patch
   */
  public static void selectPatch(int i) {
    final Game game = Game.instance();
    selectedPatch = new PatchSetter(game.playing().board(), game.getPatch(i));
  }

  /**
   * Buttons used to display and select patches
   * @return List of buttons
   */
  public static Button[] patchButtons() {
    return Arrays.copyOf(patchButtons, patchButtons.length);
  }

  /**
   * The button to end the turn of the current player.
   * @return Button
   */
  public static Button btnEndTurn() { return btnEndTurn; }

  public static PlayerButtons playerBtn(int i) { return playerBtns[i]; }

  /**
   * Wait for the next input then do an action according to it.
   * @param context Window's context
   */
  public static void manageInput(ApplicationContext context) {
    Objects.requireNonNull(context);

    // Fetch the event
    Event event;
    do {
      event = context.pollOrWaitEvent(Long.MAX_VALUE);
      // Movement of mouse is forbidden because of a graphical bug
    } while (event.getAction() == Action.POINTER_MOVE);

    // Execute action based on event
    manageEvent(event);
    
    // Update button states
    updateBtns();
  }
  
  /**
   * Execute action based on event
   * @param event User event
   */
  private static void manageEvent(Event event) {
    switch (event.getAction()) {
    case POINTER_DOWN -> {
      var pos = event.getLocation();
      Button.runButtons(new Vector((int)pos.x, (int)pos.y));
    }
    case KEY_PRESSED -> {
      if (event.getKey() == KeyboardKey.Q) System.exit(0);
    }
    default -> { }
    }
  }

  /**
   * Update buttons states, based on current game state.
   */
  private static void updateBtns() {
    final Game game = Game.instance();
    if (game.finished()) {
      // Now end turn button while quit the game
      btnEndTurn.setHandler(() -> System.exit(0));
      btnEndTurn.setText("Quit game");
    }
  }
  
  private ControlWindow() { }
}
