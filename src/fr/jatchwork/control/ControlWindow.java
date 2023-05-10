package fr.jatchwork.control;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;

public class ControlWindow {
  public static void manageInput(ApplicationContext context) {
    Event event = context.pollOrWaitEvent(Long.MAX_VALUE);
    System.out.println(event);
  }
}
