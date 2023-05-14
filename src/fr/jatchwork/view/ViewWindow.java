package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.TimeBoard;
import fr.jatchwork.model.Vector;
import fr.umlv.zen5.ScreenInfo;

public final class ViewWindow {
  private static ScreenInfo info;
  private static int squareSize = 1;

  public static float width() { return info.getWidth(); }
  public static float height() { return info.getHeight(); }
  public static int squareSize() { return squareSize; }

  public static void setScreenInfo(ScreenInfo info) {
    ViewWindow.info = info;
    
    squareSize = (int)info.getWidth() / 40;
  }

  /**
   * Update the window view.
   */
  public static void displayAll(Graphics2D graphics) {
    graphics.setColor(Color.WHITE);
    graphics.drawString("Display is empty for now :/", width() / 2, height() / 2);
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
  static void drawLine(Graphics2D graphics, Color color, Vector pos, int length, int thickness, boolean horizontal) {
    graphics.setColor(color);
    graphics.fillRect(
        pos.x() - thickness / 2,
        pos.y() - thickness / 2,
        thickness + ( horizontal ? length : 0),
        thickness + (!horizontal ? length : 0));
  }
  
  private ViewWindow() { }
}
