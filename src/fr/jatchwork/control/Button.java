package fr.jatchwork.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;

public final class Button {
  private static final List<Button> buttons = new ArrayList<>();
  
  public static List<Button> activeButtons() {
    return buttons.stream()
        .filter(button -> button.active)
        .toList();
  }
  
  /**
   * Activate handlers of all buttons which has been clicked.
   * @param clickPos Where the click happened on the window.
   */
  public static void runButtons(Vector clickPos) {
    Objects.requireNonNull(clickPos);
    buttons.stream()
      .filter(button -> button.active && button.rect.isInside(clickPos))
      .forEach(button -> button.handler.run());
  }

  private Rect rect;
  private boolean active = true;
  private Runnable handler;

  /**
   * Create a new Button.
   * @param rect The rectangle covered by the button on the window.
   * @param handler
   */
  public Button(Rect rect, Runnable handler) {
    Objects.requireNonNull(rect);
    Objects.requireNonNull(handler);
    this.rect = rect;
    this.handler = handler;
    buttons.add(this);
  }
  
  public Button(Runnable handler) {
    this(new Rect(0, 0, 0, 0), handler);
  }

  public void setActive(boolean active) { this.active = active; }

  public void setHandler(Runnable handler) {
    Objects.requireNonNull(handler);
    this.handler = handler;
  }

  public Rect rect() { return rect; }

  public void setRect(Rect rect) {
    Objects.requireNonNull(rect);
    this.rect = rect;
  }
}
