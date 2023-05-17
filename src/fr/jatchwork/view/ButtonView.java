package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.Vector;

final class ButtonView {
  private static final float RADIUS_COEF = 0.8f;
  private static final int BORDER_THICKNESS = 2;
  private static final int THREAD_THICKNESS = 2;
  private static final int THREAD_SIZE = 10;
  private static final Color BORDER_COLOR = Color.WHITE;
  private static final Color MAIN_COLOR = Color.BLUE;
  
  /**
   * Draw a button.
   * @param graphics Window's graphics
   * @param pos Position of the center of the button.
   */
  public static void drawButton(Graphics2D graphics, Vector pos) {
    int radius = (int)(ViewWindow.squareSize() * RADIUS_COEF) / 2;
    // Border
    graphics.setColor(BORDER_COLOR);
    graphics.fillOval(pos.x() - radius, pos.y() - radius, radius * 2, radius * 2);
    
    // Fill color
    graphics.setColor(MAIN_COLOR);
    graphics.fillOval(
        pos.x() - radius + BORDER_THICKNESS,
        pos.y() - radius + BORDER_THICKNESS,
        (radius - BORDER_THICKNESS) * 2,
        (radius - BORDER_THICKNESS) * 2);
    
    // Thread
    for (int i = 0; i < 2; i++) {
      HelpWindow.drawLine(graphics, BORDER_COLOR,
          pos.add(-THREAD_SIZE / 2 + THREAD_SIZE * i, -THREAD_SIZE / 2),
          THREAD_SIZE, THREAD_THICKNESS, false);
    }
  }
}
