package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import fr.jatchwork.control.Button;
import fr.jatchwork.control.ControlWindow;
import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
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
  private static int squareSize;

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
    graphics.clearRect(0, 0, width(), height());

    // Draw separators
    graphics.setColor(Color.WHITE);
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
    final Game game = Game.instance();
    
    // Time board
    final int boardMargin = 40;
    final Vector boardSize = TimeBoardView.size(game.timeBoard());
    final Vector posBoard = HelpWindow.align(rect, HelpWindow.ALIGN_TOP, new Vector(boardMargin, boardMargin), boardSize);
    TimeBoardView.drawTimeBoard(graphics, game.timeBoard(), posBoard);
    
    // Selected patch
    int posY = posBoard.y() + boardSize.y() + boardMargin;
    final int marginSelected = 20;
    final int selectedHeight = squareSize * 5 + 4;
    displayPatchInfo(graphics, new Rect(rect.x() + marginSelected, posY, rect.width() - marginSelected * 2, selectedHeight));
    
    // Patch list
    posY += selectedHeight;
    final int choiceHeight = squareSize * 4 + 4;
    displayPatchList(graphics, new Rect(rect.x(), posY, rect.width(), choiceHeight));
  }
  
  private static void displayPatchInfo(Graphics2D graphics, Rect rect) {
    final Patch patch = ControlWindow.getSelectedPatch();
    final int boxBorderSize = 2;
    final int textSpace = (int)(FONT_SIZE);
    final Vector marginText = new Vector(20, (rect.height() - FONT_SIZE * 3) / 2);
    
    // Draw patch box
    HelpWindow.drawRect(graphics, new Rect(rect.x(), rect.y(), rect.height(), rect.height()), boxBorderSize, Color.WHITE, Color.DARK_GRAY);
    
    Vector textPos = new Vector(rect.x() + rect.height() + marginText.x(), rect.y() + marginText.y());
    if (patch != null) {
      // Draw patch
      PatchView.drawPatchCenter(graphics, patch, new Vector(rect.x() + rect.height() / 2, rect.y() + rect.height() / 2));
      // Draw informative text
      graphics.setColor(Color.WHITE);
      HelpWindow.drawText(graphics, "time cost : " + patch.timeCost(), FONT, textPos);
      textPos = textPos.add(0, textSpace);
      HelpWindow.drawText(graphics, "button cost : " + patch.buttonCost(), FONT, textPos);
      textPos = textPos.add(0, textSpace);
      HelpWindow.drawText(graphics, "button income : " + patch.buttonIncome(), FONT, textPos);
    } else {
      // Draw default informative text
      graphics.setColor(Color.GRAY);
      HelpWindow.drawText(graphics, "Select a patch", FONT, textPos);
      textPos = textPos.add(0, textSpace);
      HelpWindow.drawText(graphics, "to display its", FONT, textPos);
      textPos = textPos.add(0, textSpace);
      HelpWindow.drawText(graphics, "informations", FONT, textPos);
    }
  }
  
  private static void displayPatchList(Graphics2D graphics, Rect rect) {
    final Game game = Game.instance();
    final int butSize = rect.width() / 3;
    Button[] buttons = ControlWindow.patchButtons();
    for (int i = 0; i < buttons.length; i++) {
      // Draw button patch
      buttons[i].setRect(new Rect(
          rect.x() + butSize * i,
          rect.y(),
          butSize,
          rect.height()));
      HelpWindow.drawButton(graphics, buttons[i]);
      
      // Draw patch inside button
      PatchView.drawPatchInside(graphics, game.getPatch(i), buttons[i].rect());
    }
  }
  
  private ViewWindow() { }
}
