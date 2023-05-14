package fr.jatchwork.control;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class ControlWindow {
  private ControlWindow() { }
  
  public static void manageInput(ApplicationContext context) {
    Event event = context.pollOrWaitEvent(Long.MAX_VALUE);
    System.out.println(event);
    
    if (event.getAction() == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
      System.exit(0);
    }
  }
}
