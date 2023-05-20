package fr.jatchwork.control;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;

/**
 * A clickable button, containing a task to execute.
 */
public final class Button {
  /**
   * All created buttons.
   */
  private static final List<Button> buttons = new ArrayList<>();
  
  /**
   * List of all currently active buttons.
   * @return List of buttons
   */
  public static List<Button> activeButtons() {
    return buttons.stream()
        .filter(button -> button.active)
        .toList();
  }
  
  /**
   * Activate handlers of all buttons which has been clicked.
   * @param clickPos Where the click happened on the window
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
  private String text;
  private Font font;
  private Color textColor;

  /**
   * Create a new Button.
   * @param rect The rectangle covered by the button on the window.
   * @param handler task to execute when clicked
   */
  public Button(Runnable handler, Rect rect, String text, Font font, Color textColor) {
    Objects.requireNonNull(handler);
    this.handler = handler;
    this.rect = rect;
    this.text = text;
    this.font = font;
    this.textColor = textColor;
    buttons.add(this);
  }

  /**
   * Create a new Button.
   * @param handler task to execute when clicked
   * @param rect The rectangle covered by the button on the window.
   */
  public Button(Runnable handler, Rect rect) {
    this(handler, rect, null, null, null);
  }

  /**
   * Create a new button, without a position and size.
   * @param handler Task to execute when clicked
   */
  public Button(Runnable handler) {
    this(handler, null, null, null, null);
  }

  /**
   * Activate or deactivate the button.
   * A deactivated button will not be displayed.
   * @param active New state of the button
   */
  public void setActive(boolean active) { this.active = active; }

  /**
   * Change the task to run when clicked.
   * @param handler The new task
   */
  public void setHandler(Runnable handler) {
    this.handler = Objects.requireNonNull(handler);
  }

  /**
   * Position and dimension of the button.
   * @return Current rect
   */
  public Rect rect() { return rect; }

  /**
   * Change the position and size of the button.
   * @param rect New rectangle
   */
  public void setRect(Rect rect) {
    this.rect = rect;
  }
  
  public String text() { return text; }
  
  public Font font() { return font; }

  public void setText(String text) {
    this.text = text;
  }

  public Color textColor() { return textColor; }
}
