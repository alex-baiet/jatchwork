package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import fr.jatchwork.control.Button;
import fr.jatchwork.control.ControlWindow;
import fr.jatchwork.control.PlayerButtons;
import fr.jatchwork.model.Game;
import fr.jatchwork.model.Patch;
import fr.jatchwork.model.Player;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;
import fr.umlv.zen5.ScreenInfo;

/**
 * Manage display for windowed versions.
 */
public final class ViewWindow {
  public static final Color PLAYER1_COLOR = Color.CYAN;
  public static final Color PLAYER2_COLOR = Color.GREEN;
  
  private static final int FONT_SIZE = 40;
  public static final Font FONT = new Font("Arial", Font.PLAIN, HelpWindow.pixelToPoint(FONT_SIZE));
  private static final Font FONT_TITLE = new Font("Arial", Font.PLAIN, HelpWindow.pixelToPoint(80));
  private static final Font FONT_ACTION = new Font("Arial", Font.BOLD, HelpWindow.pixelToPoint(60));
  
  private static ScreenInfo info;
  private static int squareSize;

  /**
   * Width of the window.
   * @return Width
   */
  public static int width() { return (int)info.getWidth(); }

  /**
   * Height of the window
   * @return height
   */
  public static int height() { return (int)info.getHeight(); }

  /**
   * Size of a square for patches, time board and quilt board.
   * @return Square's size.
   */
  public static int squareSize() { return squareSize; }

  /**
   * Give information about the window to initialize ViewWindow.
   * @param info Contains all required informations for initialization
   */
  public static void setScreenInfo(ScreenInfo info) {
    ViewWindow.info = info;
    
    squareSize = (int)info.getWidth() / 40;
  }

  /**
   * Update the window view.
   * @param graphics Window's graphics
   */
  public static void displayAll(Graphics2D graphics) {
    Objects.requireNonNull(graphics);
    Game game = Game.instance();
    graphics.clearRect(0, 0, width(), height());

    // Draw separators
    graphics.setColor(Color.WHITE);
    for (int i = 1; i < 3; i++) {
      HelpWindow.drawLine(graphics, Color.WHITE, new Vector(width() / 3 * i, 0), height(), 4, false);
    }

    // Draw players
    displayPlayer(graphics, new Rect(0, 0, width() / 3, height()), game.player(0), PLAYER1_COLOR);
    displayPlayer(graphics, new Rect(width() * 2 / 3, 0, width() / 3, height()), game.player(1), PLAYER2_COLOR);

    // Draw center
    displayCommon(graphics, new Rect(width() / 3, 0, width() / 3, height()));
  }

  /**
   * Draw the section of the window with all informations about a player.
   * @param graphics Window's graphics
   * @param rect Where to draw
   * @param player Player to draw
   * @param titleColor Color of the title
   */
  private static void displayPlayer(Graphics2D graphics, Rect rect, Player player, Color titleColor) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rect);
    Objects.requireNonNull(player);
    final int titleMarginX = 60;
    final int marginX = 100;
    final int spaceY = (int)(FONT_SIZE * 1.2f);
    int posY = 40;
    
    // Title
    graphics.setColor(titleColor);
    HelpWindow.drawText(graphics, "Player " + player.numero(), FONT_TITLE, rect.pos().add(titleMarginX, posY));

    // "Your turn" text
    graphics.setColor(Color.WHITE);
    posY += 100;
    if (Game.instance().playing() == player) {
      HelpWindow.drawText(graphics, "YOUR TURN", FONT_ACTION, rect.pos().add(titleMarginX, posY));
    }

    // Player statistics
    HelpWindow.drawText(graphics,
        "buttons : " + player.buttonCount() +
        "\nincome : " + player.buttonIncome() +
        "\nscore : " + player.score(),
        FONT, rect.pos().add(marginX, posY += 120), spaceY);

    // Quilt board
    final int boardWidth = QuiltBoardView.size(player.board()).x();
    Vector pos = HelpWindow.align(rect, HelpWindow.ALIGN_BOTTOM, new Vector(40, 40), QuiltBoardView.size(player.board()));
    QuiltBoardView.drawQuiltBoard(graphics, player.board(), pos);
    
    // Buttons
    final int marginBtn = 20;
    final int heightBtn = 60;
    pos = pos.add(0, -(marginBtn + heightBtn) * 2);
    displayPlayerBtns(
        graphics,
        ControlWindow.playerBtn(player.numero()-1),
        new Rect(pos.x(), pos.y(), boardWidth, heightBtn),
        marginBtn);
  }
  
  private static void displayPlayerBtns(Graphics2D graphics, PlayerButtons btns, Rect rect, int margin) {
    final int widthBtn = (rect.width() - margin) / 2;
    // Define buttons positions and size
    btns.buy().setRect(new Rect(rect.x(), rect.y(), rect.width(), rect.height()));
    btns.rotate().setRect(new Rect(rect.x(), rect.y() + margin + rect.height(), widthBtn, rect.height()));
    btns.flip().setRect(new Rect(rect.x() + widthBtn + margin, rect.y() + margin + rect.height(), widthBtn, rect.height()));
    // Draw buttons
    HelpWindow.drawButton(graphics, btns.rotate());
    HelpWindow.drawButton(graphics, btns.flip());
    HelpWindow.drawButton(graphics, btns.buy());
  }

  /**
   * Draw the section of the window with the time board and the list of patches.
   * @param graphics Window's graphics
   * @param rect Where to draw
   */
  private static void displayCommon(Graphics2D graphics, Rect rect) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rect);
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
    
    // End turn button
    drawBtnEndTurn(graphics, rect);
  }

  /**
   * Draw patch information section.
   * @param graphics Window's graphics
   * @param rect Where to draw
   */
  private static void displayPatchInfo(Graphics2D graphics, Rect rect) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rect);
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
      HelpWindow.drawText(graphics,
          "time cost : " + patch.timeCost() +
          "\nbutton cost : " + patch.buttonCost() +
          "\nbutton income : " + patch.buttonIncome(),
          FONT, textPos, textSpace);
    } else {
      // Draw default informative text
      graphics.setColor(Color.GRAY);
      HelpWindow.drawText(graphics, "Select a patch\nto display its\ninformations", FONT, textPos, textSpace);
    }
  }

  /**
   * Draw section for the list of patches
   * @param graphics Window's graphics
   * @param rect Where to draw and size of the section
   */
  private static void displayPatchList(Graphics2D graphics, Rect rect) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rect);
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
  
  private static void drawBtnEndTurn(Graphics2D graphics, Rect rectSection) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(rectSection);
    
    // Define button position
    final Vector size = new Vector(rectSection.width() / 2, 80);
    final Vector pos = HelpWindow.align(rectSection, HelpWindow.ALIGN_BOTTOM, new Vector(0, 40), size);
    Button btn = ControlWindow.btnEndTurn();
    btn.setRect(new Rect(pos.x(), pos.y(), size.x(), size.y()));

    // Draw button and its text
    HelpWindow.drawButton(graphics, btn);
  }
  
  private ViewWindow() { }
}
