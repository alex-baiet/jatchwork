package fr.jatchwork.control;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
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
  private static Patch selectedPatch = null;

  private static Button[] patchButtons = new Button[] {
      new Button(() -> selectPatch(0)),
      new Button(() -> selectPatch(1)),
      new Button(() -> selectPatch(2)),
  };

  private static Button btnEndTurn = new Button(
      () -> Game.instance().playing().endTurn(),
      null, "End turn", ViewWindow.FONT, Color.WHITE);

  /**
   * The selected patch from the instance of Game.
   * @return Patch
   */
  public static Patch getSelectedPatch() { return selectedPatch; }

  /**
   * Change the selected patch.
   * @param i Index of the new selected patch
   */
  public static void selectPatch(int i) {
    selectedPatch = Game.instance().getPatch(i);
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

  /**
   * Wait for the next input then do an action according to it.
   * @param context Window's context
   */
  public static void manageInput(ApplicationContext context) {
    Objects.requireNonNull(context);
    Event event;
    do {
      event = context.pollOrWaitEvent(Long.MAX_VALUE);
      // Movement of mouse is forbidden because of a graphical bug
    } while (event.getAction() == Action.POINTER_MOVE);

    System.out.println(event);

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

  private ControlWindow() { }
}
