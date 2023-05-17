package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;

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

  /**
   * Get a position aligned in a 2D space.
   * @param dest Box where the item must be aligned
   * @param alignment See HelpWindow.ALIGN_* for all possibilities
   * @param margin Space between the item to place and borders
   * @param size Size of the item to align
   * @return The aligned position
   */
  public static Vector align(Rect dest, Vector alignment, Vector margin, Vector size) {
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
    graphics.setColor(color);
    graphics.fillRect(
        pos.x() - thickness / 2,
        pos.y() - thickness / 2,
        thickness + ( horizontal ? length : 0),
        thickness + (!horizontal ? length : 0));
  }

}
