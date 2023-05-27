package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import fr.jatchwork.model.Game;
import fr.jatchwork.model.Player;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.TimeBoard;
import fr.jatchwork.model.Vector;

/**
 * Contains methods to manage time board visually.
 */
final class TimeBoardView {
  
  /** Maximum number of square horizontally */
  private static final int MAX_SQUARE_COUNT = 10;
  private static final Color MAIN_COLOR = Color.DARK_GRAY;

  private static final int BORDER_WIDTH = 2;
  private static final Color BORDER_COLOR = Color.GRAY;

  private static final int BORDER_PATH_WIDTH = 4;
  private static final Color BORDER_PATH_COLOR = Color.WHITE;

  /**
   * Draw the time board in a zig-zag format.
   * @param graphics Window's graphics
   * @param board What to draw
   * @param Where to draw the top left corner
   */
  public static void drawTimeBoard(Graphics2D graphics, TimeBoard board, Vector pos) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(board);
    Objects.requireNonNull(pos);

    // Draw squares and fill color
    for (int i = 0; i < board.size(); i++) {
      drawSquare(graphics, squarePos(pos, i));
    }

    // Draw path delimiter borders
    for (int i = 0; i < board.size(); i++) {
      drawPathBorders(graphics, squarePos(pos, i), i, board.size());
    }

    // Draw buttons
    for (int i = 1; i < board.size(); i++) {
      if (board.containsIncome(i-1, i) > 0) {
        ButtonView.drawButton(graphics, getBetweenSquarePos(pos, i));
      }
    }

    // Draw patches
    drawPatches(graphics, pos, board);

    // Draw players
    drawPlayers(graphics, pos);
  }
  
  /**
   * Draw patches on the board.
   * @param graphics Window's graphics
   * @param boardPos Position of the board on the window
   * @param board The board being drawn
   */
  private static void drawPatches(Graphics2D graphics, Vector boardPos, TimeBoard board) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(boardPos);
    Objects.requireNonNull(board);
    final int leatherSize = (int)(ViewWindow.squareSize() * 0.8f);
    for (int i = 1; i < board.size(); i++) {
      if (board.containsLeathers(i-1, i) > 0) {
        Vector posl = getBetweenSquarePos(boardPos, i).add(-leatherSize/2, -leatherSize/2);
        Rect rect = new Rect(posl.x(), posl.y(), leatherSize, leatherSize);
        HelpWindow.drawRect(graphics, rect, 2, Color.WHITE, Color.GRAY);
      }
    }
  }

  /**
   * Draw players on the time board.
   * @param graphics Window's graphics
   * @param boardPos Position of the board
   */
  private static void drawPlayers(Graphics2D graphics, Vector boardPos) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(boardPos);
    final int square = ViewWindow.squareSize();
    final Game game = Game.instance();
    final int sizePlayer = (int)(square * 0.4f);
    final int margin = (square/2 - sizePlayer) / 2;
    for (int i = 0; i < Game.PLAYER_COUNT; i++) {
      Player player = game.player(i);
      Vector posSquare = squarePos(boardPos, player.position());
      Vector posPlayer = HelpWindow.align(
          new Rect(posSquare.x(), posSquare.y(), square, square),
          i == 0 ? HelpWindow.ALIGN_TOP : HelpWindow.ALIGN_BOTTOM,
          new Vector(margin, margin),
          new Vector(sizePlayer, sizePlayer));
      Rect rectPlayer = new Rect(posPlayer.x(), posPlayer.y(), sizePlayer, sizePlayer);
      HelpWindow.drawRect(graphics, rectPlayer, 2, Color.WHITE, i == 0 ? ViewWindow.PLAYER1_COLOR : ViewWindow.PLAYER2_COLOR);
    }
  }
  
  /**
   * Get the position of a square.
   * @param basePos Top left corner of the time board
   * @param i Index of square
   * @return Top left corner of the square in pixel
   */
  private static Vector squarePos(Vector basePos, int i) {
    Objects.requireNonNull(basePos);
    int ysquare = i / MAX_SQUARE_COUNT;
    int xsquare = i % MAX_SQUARE_COUNT;
    boolean reversed = ysquare % 2 == 1;
    int square = ViewWindow.squareSize();
    return basePos.add(
        reversed ? (MAX_SQUARE_COUNT - xsquare - 1) * square : xsquare * square,
        ysquare * square);
  }
  
  /**
   * Draw a single square of the time board.
   * @param graphics Window's graphics
   * @param pos Position of the top left corner
   * @param i Index of square inside the time board
   * @param count Count of squares inside the time board
   */
  private static void drawSquare(Graphics2D graphics, Vector pos) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(pos);
    int square = ViewWindow.squareSize();
    // Fill the square with color
    graphics.setColor(MAIN_COLOR);
    graphics.fillRect(pos.x(), pos.y(), square, square);
    
    // Draw borders
    // top
    HelpWindow.drawLine(graphics, BORDER_COLOR, pos, square, BORDER_WIDTH, true);
    // left
    HelpWindow.drawLine(graphics, BORDER_COLOR, pos, square, BORDER_WIDTH, false);
    // bottom
    HelpWindow.drawLine(graphics, BORDER_COLOR, pos.add(0, square), square, BORDER_WIDTH, true);
    // right
    HelpWindow.drawLine(graphics, BORDER_COLOR, pos.add(square, 0), square, BORDER_WIDTH, false);
  }
  
  /**
   * Draw thicker border to delimit visually the path around a single square.
   * @param graphics Window's graphics
   * @param pos Position of square
   * @param i Current square
   * @param count Total number of square
   */
  private static void drawPathBorders(Graphics2D graphics, Vector pos, int i, int count) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(pos);

    int square = ViewWindow.squareSize();
    // direction of the line
    int direction = (i / MAX_SQUARE_COUNT) % 2;
    // top
    if (i == 0 || i % MAX_SQUARE_COUNT != 0) {
      HelpWindow.drawLine(graphics, BORDER_PATH_COLOR, pos, square, BORDER_PATH_WIDTH, true);
    }
    // left
    if (direction == 0 && i % MAX_SQUARE_COUNT == 0 ||
        direction == 1 && (i % MAX_SQUARE_COUNT == MAX_SQUARE_COUNT-1 || i == count-1)) {
      HelpWindow.drawLine(graphics, BORDER_PATH_COLOR, pos, square, BORDER_PATH_WIDTH, false);
    }

    // bottom
    if (i == count-1 || i % MAX_SQUARE_COUNT != MAX_SQUARE_COUNT - 1) {
      HelpWindow.drawLine(graphics, BORDER_PATH_COLOR, pos.add(0, square), square, BORDER_PATH_WIDTH, true);
    }

    // right
    if (direction == 1 && i % MAX_SQUARE_COUNT == 0 ||
        direction == 0 && (i % MAX_SQUARE_COUNT == MAX_SQUARE_COUNT-1 || i == count-1)) {
      HelpWindow.drawLine(graphics, BORDER_PATH_COLOR, pos.add(square, 0), square, BORDER_PATH_WIDTH, false);
    }
  }

  /**
   * Get the position between given square and the previous one.
   * @param basePos 
   * @param i
   * @return Centered position on the line between the two squares
   */
  private static Vector getBetweenSquarePos(Vector basePos, int i) {
    Objects.requireNonNull(basePos);
    int square = ViewWindow.squareSize();
    return squarePos(basePos, i).add(squarePos(basePos, i-1)).add(square, square).multiply(0.5f);
  }
  
  /**
   * Get the size of the board on the screen.
   * @param board
   * @return Size in pixel
   */
  public static Vector size(TimeBoard board) {
    Objects.requireNonNull(board);
    int square = ViewWindow.squareSize();
    return new Vector(
        (board.size() < MAX_SQUARE_COUNT ? board.size() : MAX_SQUARE_COUNT) * square,
        ((board.size()-1) / MAX_SQUARE_COUNT + 1) * square);
  }

  private TimeBoardView() { }
}
