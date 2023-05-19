package fr.jatchwork.control;

import java.util.Arrays;
import java.util.Objects;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
import fr.jatchwork.model.Vector;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;

public final class ControlWindow {

  // Temporary default patch for test, to delete
  private static Patch selectedPatch = null;

  private static Button[] patchButtons = new Button[] {
      new Button(() -> selectPatch(0)),
      new Button(() -> selectPatch(1)),
      new Button(() -> selectPatch(2)),
  };

  public static Patch getSelectedPatch() { return selectedPatch; }

  public static void selectPatch(int i) {
    selectedPatch = Game.instance().getPatch(i);
  }
  
  public static Button[] patchButtons() {
    return Arrays.copyOf(patchButtons, patchButtons.length);
  }
  
  public static void manageInput(ApplicationContext context) {
    Objects.requireNonNull(context);
    Event event = context.pollOrWaitEvent(Long.MAX_VALUE);
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
