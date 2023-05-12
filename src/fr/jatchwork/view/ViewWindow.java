package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import fr.umlv.zen5.ScreenInfo;

public class ViewWindow {
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
    Random r = new Random();
    graphics.setColor(Color.WHITE);
    graphics.clearRect(0, 0, 0, 0);
    Rectangle rect = new Rectangle(r.nextInt(200), r.nextInt(200), 4, 4);
    graphics.drawRect(r.nextInt(200), r.nextInt(200), 2, 2);
    graphics.fillRect(r.nextInt(200), r.nextInt(200), 2, 2);
    //graphics.draw(rect);
  }
}
