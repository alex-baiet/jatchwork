package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.Patch;
import fr.jatchwork.model.Vector;
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
    graphics.setColor(Color.WHITE);
    graphics.drawString("Display is empty for now :/", width() / 2, height() / 2);
  }
  
  private ViewWindow() { }
}
