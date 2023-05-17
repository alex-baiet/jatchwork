package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Player;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;
import fr.umlv.zen5.ScreenInfo;

public final class ViewWindow {
  private static ScreenInfo info;
  private static int squareSize = 1;

  public static int width() { return (int)info.getWidth(); }
  public static int height() { return (int)info.getHeight(); }
  public static int squareSize() { return squareSize; }

  public static void setScreenInfo(ScreenInfo info) {
    ViewWindow.info = info;
    
    squareSize = (int)info.getWidth() / 40;
  }

  /**
   * Update the window view.
   */
  public static void displayAll(Graphics2D graphics) {
    Game game = Game.instance();
    graphics.setColor(Color.WHITE);

    // Draw separators
    for (int i = 1; i < 3; i++) {
      HelpWindow.drawLine(graphics, Color.WHITE, new Vector(width() / 3 * i, 0), height(), 4, false);
    }

    // Draw players
    displayPlayer(graphics, new Rect(0, 0, width() / 3, height()), game.player(0));
    displayPlayer(graphics, new Rect(width() * 2 / 3, 0, width() / 3, height()), game.player(1));

    // Draw center
    displayCommon(graphics, new Rect(width() / 3, 0, width() / 3, height()));
  }

  private static void displayPlayer(Graphics2D graphics, Rect rect, Player player) {
    Vector pos = HelpWindow.align(rect, HelpWindow.ALIGN_TOP, new Vector(40, 40), QuiltBoardView.size(player.board()));
    QuiltBoardView.drawQuiltBoard(graphics, player.board(), pos);
  }

  private static void displayCommon(Graphics2D graphics, Rect rect) {
    TimeBoardView.drawTimeBoard(graphics, Game.instance().timeBoard(), rect.pos());
  }
  
  private ViewWindow() { }
}
