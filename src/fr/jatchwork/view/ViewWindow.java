package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Player;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;
import fr.umlv.zen5.ScreenInfo;

public final class ViewWindow {
  private static final int FONT_SIZE = 40;
  private static final Font FONT = new Font("Arial", Font.PLAIN, HelpWindow.pixelToPoint(FONT_SIZE));
  private static final Font FONT_TITLE = new Font("Arial", Font.PLAIN, HelpWindow.pixelToPoint(80));
  private static final Font FONT_ACTION = new Font("Arial", Font.BOLD, HelpWindow.pixelToPoint(60));
  
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

  /**
   * Draw the section of the window with all informations about a player.
   * @param graphics Window's graphics
   * @param rect Where to draw
   * @param player Player to draw
   */
  private static void displayPlayer(Graphics2D graphics, Rect rect, Player player) {
    final int titleMarginX = 60;
    final int marginX = 100;
    final int spaceY = (int)(FONT_SIZE * 1.5f);
    int posY = 40;
    HelpWindow.drawText(graphics, "Player " + player.numero(), FONT_TITLE, rect.pos().add(titleMarginX, posY));
    
    posY += 100;
    if (Game.instance().playing() == player) {
      HelpWindow.drawText(graphics, "YOUR TURN", FONT_ACTION, rect.pos().add(titleMarginX, posY));
    }
    
    HelpWindow.drawText(graphics, "buttons : " + player.buttonCount(), FONT, rect.pos().add(marginX, posY += 120));
    HelpWindow.drawText(graphics, "income : " + player.buttonIncome(), FONT, rect.pos().add(marginX, posY += spaceY));
    HelpWindow.drawText(graphics, "score : " + player.score(), FONT, rect.pos().add(marginX, posY += spaceY));

    Vector pos = HelpWindow.align(rect, HelpWindow.ALIGN_BOTTOM, new Vector(40, 40), QuiltBoardView.size(player.board()));
    QuiltBoardView.drawQuiltBoard(graphics, player.board(), pos);
  }

  /**
   * Draw the section of the window with the time board and the list of patches.
   * @param graphics Window's graphics
   * @param rect Where to draw
   */
  private static void displayCommon(Graphics2D graphics, Rect rect) {
    Game game = Game.instance();
    Vector pos = HelpWindow.align(rect, HelpWindow.ALIGN_TOP, new Vector(40, 40), TimeBoardView.size(game.timeBoard()));
    TimeBoardView.drawTimeBoard(graphics, game.timeBoard(), pos);
  }
  
  private ViewWindow() { }
}
