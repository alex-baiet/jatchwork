package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Objects;

import fr.jatchwork.control.Button;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;

/**
 * Contains various functions to draw on a window.
 */
final class HelpWindow {
  public static final Vector ALIGN_CENTER = new Vector(0, 0);
  public static final Vector ALIGN_LEFT = new Vector(-1, 0);
  public static final Vector ALIGN_RIGHT = new Vector(1, 0);
  public static final Vector ALIGN_TOP = new Vector(0, -1);
  public static final Vector ALIGN_BOTTOM = new Vector(0, 1);
  public static final Vector ALIGN_TOP_LEFT = new Vector(-1, -1);
  public static final Vector ALIGN_TOP_RIGHT = new Vector(1, -1);
  public static final Vector ALIGN_BOTTOM_LEFT = new Vector(-1, 1);
  public static final Vector ALIGN_BOTTOM_RIGHT = new Vector(1, 1);
  
  private static final int DEFAULT_FONT_SIZE = 12;

  /**
   * Get a position aligned in a 2D space.
   * @param dest Box where the item must be aligned
   * @param alignment See HelpWindow.ALIGN_* for all possibilities
   * @param margin Space between the item to place and borders
   * @param size Size of the item to align
   * @return The aligned position
   */
  public static Vector align(Rect dest, Vector alignment, Vector margin, Vector size) {
    Objects.requireNonNull(dest);
    Objects.requireNonNull(alignment);
    Objects.requireNonNull(margin);
    Objects.requireNonNull(size);
    return new Vector(
        alignLine(dest.x(), dest.width(), alignment.x(), margin.x(), size.x()),
        alignLine(dest.y(), dest.height(), alignment.y(), margin.y(), size.y()));
  }

  /**
   * Get a position aligned on a line, or in an 1D space.
   * @param basePos Line's position on which we need to get the position
   * @param lineSize Size of the line container
   * @param alignment -1 for left, 0 for center, 1 for right
   * @param margin Mandatory space between the final position and ends of line
   * @param itemSize Size of the item to place
   * @return The aligned position
   */
  private static int alignLine(int basePos, int lineSize, int alignment, int margin, int itemSize) {
    return switch (alignment) {
    case -1 -> basePos + margin;
    case 0 -> basePos + (lineSize - itemSize) / 2;
    case 1 -> basePos + lineSize - margin - itemSize;
    default -> throw new IllegalArgumentException("'alignment' value should be -1, 0 or 1.");
    };
  }

  /**
   * Draw a line on the window with a thickness horizontally or vertically
   * @param graphics Window's graphics
   * @param color Color of the line
   * @param pos Top left position of the line
   * @param length Length of the line
   * @param thickness Width of the line
   * @param horizontal Should we draw the line horizontally of vertically
   */
  public static void drawLine(Graphics2D graphics, Color color, Vector pos, int length, int thickness, boolean horizontal) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(color);
    Objects.requireNonNull(pos);
    graphics.setColor(color);
    graphics.fillRect(
        pos.x() - thickness / 2,
        pos.y() - thickness / 2,
        thickness + ( horizontal ? length : 0),
        thickness + (!horizontal ? length : 0));
  }

  /**
   * Draw a text on the screen.
   * @param Graphics2D graphics
   * @param string Text to draw
   * @param font Font of the text
   * @param pos Position in pixels
   */
  public static void drawText(Graphics2D graphics, String string, Font font, Vector pos) {
    if (string == null || string.isEmpty()) return;
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(font);
    Objects.requireNonNull(pos);
    graphics.setFont(font);
    graphics.drawString(
        string,
        pos.x(),
        pos.y() + pointToPixel(font.getSize() - DEFAULT_FONT_SIZE));
  }

  /**
   * Draw a text centered inside a box
   * @param Graphics2D graphics
   * @param string Text to draw
   * @param font Font of the text
   * @param rect Box to center the text in
   */
  public static void drawTextCenter(Graphics2D graphics, String string, Font font, Rect rect) {
    if (string == null || string.isEmpty()) return;
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(font);
    Objects.requireNonNull(rect);

    FontMetrics metrics = graphics.getFontMetrics(font);
    Vector size = new Vector(metrics.stringWidth(string), metrics.getHeight());
    drawText(graphics, string, font, rect.pos().add(rect.size().sub(size).multiply(0.5f)));
  }

  /**
   * Draw a text on the screen.
   * @param Graphics2D graphics
   * @param string Text to draw
   * @param font Font of the text
   * @param pos Position in pixels
   * @param Space between lines
   */
  public static void drawText(Graphics2D graphics, String string, Font font, Vector pos, int lineSpace) {
    if (string == null || string.isEmpty()) return;
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(font);
    Objects.requireNonNull(pos);
    int i = 0;
    for (String line : string.split("\n")) {
      drawText(graphics, line, font, pos.add(0, i++ * lineSpace));
    }
  }

  /**
   * Convert pixel size into point size.
   * @param pixelSize
   * @return Converted value
   */
  public static int pixelToPoint(int pixelSize) {
    return (int)(72.0f * (float)pixelSize / Toolkit.getDefaultToolkit().getScreenResolution());
  }

  /**
   * Convert point size to pixel size
   * @param pointSize
   * @return Converted value
   */
  public static int pointToPixel(int pointSize) {
    return pointSize * Toolkit.getDefaultToolkit().getScreenResolution() / 72;
  }

  /**
   * Draw a rectangle with a border.
   * @param graphics Window's graphics
   * @param rect Position and size
   * @param borderSize Thickness of the border
   * @param borderColor Border color
   * @param fillColor Color inside the rectangle
   */
  public static void drawRect(Graphics2D graphics, Rect rect, int borderSize, Color borderColor, Color fillColor) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rect);
    Objects.requireNonNull(borderColor);
    Objects.requireNonNull(fillColor);
    graphics.setColor(borderColor);
    // TODO: to optimize the draw, use drawRect in a loop instead to draw the border
    graphics.fillRect(rect.x(), rect.y(), rect.width(), rect.height());
    graphics.setColor(fillColor);
    graphics.fillRect(
        rect.x() + borderSize,
        rect.y() + borderSize,
        rect.width() - borderSize * 2,
        rect.height() - borderSize * 2);
  }

  /**
   * Draw a button (the clickable button, not the currency)
   * @param graphics Window's graphics
   * @param button Button to draw
   */
  public static void drawButton(Graphics2D graphics, Button button) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(button);
    // Draw only if button is active
    if (!button.active()) return;
    // Draw button box
    drawRect(graphics, button.rect(), 2, Color.WHITE, Color.DARK_GRAY);
    // Draw button label
    graphics.setColor(Color.WHITE);
    drawTextCenter(graphics, button.text(), button.font(), button.rect());
  }
}
