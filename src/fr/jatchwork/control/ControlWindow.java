package fr.jatchwork.control;

import fr.jatchwork.model.Patch;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class ControlWindow {

  // Temporary default patch for test, to delete
  private static Patch selectedPatch = new Patch(2, 4, 5, """
      #..#
      ####
      .##.
      """);

  public static Patch getSelectedPatch() { return selectedPatch; }

  public static void manageInput(ApplicationContext context) {
    Event event = context.pollOrWaitEvent(Long.MAX_VALUE);
    System.out.println(event);
    
    if (event.getAction() == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
      System.exit(0);
    }
  }

  private ControlWindow() { }
}
