package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

import fr.jatchwork.model.TimeBoard;
import fr.jatchwork.model.Vector;

final class TimeBoardView {
  
  /** Maximum number of square horizontally */
  private static final int MAX_SQUARE_COUNT = 10;
  private static final Color MAIN_COLOR = Color.DARK_GRAY;
  private static final Color BORDER_COLOR = Color.GRAY;
  private static final Color BORDER_PATH_COLOR = Color.WHITE;
  private static final int BORDER_WIDTH = 2;
  private static final int BORDER_PATH_WIDTH = 4;
  
  /**
   * Draw the time board in a zig-zag format.
   * @param graphics Window's graphics
   * @param board What to draw
   * @param Where to draw the top left corner
   */
  public static void drawTimeBoard(Graphics2D graphics, TimeBoard board, Vector pos) {
    int square = ViewWindow.squareSize();

    for (int i = 0; i < board.size(); i++) {
      int ysquare = i / MAX_SQUARE_COUNT;
      int xsquare = i % MAX_SQUARE_COUNT;
      boolean reversed = ysquare % 2 == 1;
      drawSquare(graphics, pos.add(
          reversed ? (MAX_SQUARE_COUNT - xsquare - 1) * square : xsquare * square,
          ysquare * square));
    }

    for (int i = 0; i < board.size(); i++) {
      int ysquare = i / MAX_SQUARE_COUNT;
      int xsquare = i % MAX_SQUARE_COUNT;
      boolean reversed = ysquare % 2 == 1;

      drawPathBorders(graphics, pos.add(
          reversed ? (MAX_SQUARE_COUNT - xsquare - 1) * square : xsquare * square,
          ysquare * square),
          i, board.size());
    }
  }
  
  /**
   * Draw a single square of the time board.
   * @param graphics Window's graphics
   * @param pos Position of the top left corner
   * @param i Index of square inside the time board
   * @param count Count of squares inside the time board
   */
  private static void drawSquare(Graphics2D graphics, Vector pos) {
    int square = ViewWindow.squareSize();
    // Fill the square with color
    graphics.setColor(MAIN_COLOR);
    graphics.fillRect(pos.x(), pos.y(), square, square);
    
    // Draw borders
    // top
    ViewWindow.drawLine(graphics, BORDER_COLOR, pos, square, BORDER_WIDTH, true);
    // left
    ViewWindow.drawLine(graphics, BORDER_COLOR, pos, square, BORDER_WIDTH, false);
    // bottom
    ViewWindow.drawLine(graphics, BORDER_COLOR, pos.add(0, square), square, BORDER_WIDTH, true);
    // right
    ViewWindow.drawLine(graphics, BORDER_COLOR, pos.add(square, 0), square, BORDER_WIDTH, false);
  }
  
  /**
   * Draw thicker border to delimit visually the path around a single square.
   * @param graphics Window's graphics
   * @param pos Position of square
   * @param i Current square
   * @param count Total number of square
   */
  private static void drawPathBorders(Graphics2D graphics, Vector pos, int i, int count) {
    int square = ViewWindow.squareSize();
    // direction of the line
    int direction = (i / MAX_SQUARE_COUNT) % 2;
    // top
    if (i == 0 || i % MAX_SQUARE_COUNT != 0) {
      ViewWindow.drawLine(graphics, BORDER_PATH_COLOR, pos, square, BORDER_PATH_WIDTH, true);
    }
    // left
    if (direction == 0 && i % MAX_SQUARE_COUNT == 0 ||
        direction == 1 && (i % MAX_SQUARE_COUNT == MAX_SQUARE_COUNT-1 || i == count-1)) {
      ViewWindow.drawLine(graphics, BORDER_PATH_COLOR, pos, square, BORDER_PATH_WIDTH, false);
    }

    // bottom
    if (i == count-1 || i % MAX_SQUARE_COUNT != MAX_SQUARE_COUNT - 1) {
      ViewWindow.drawLine(graphics, BORDER_PATH_COLOR, pos.add(0, square), square, BORDER_PATH_WIDTH, true);
    }

    // right
    if (direction == 1 && i % MAX_SQUARE_COUNT == 0 ||
        direction == 0 && (i % MAX_SQUARE_COUNT == MAX_SQUARE_COUNT-1 || i == count-1)) {
      ViewWindow.drawLine(graphics, BORDER_PATH_COLOR, pos.add(square, 0), square, BORDER_PATH_WIDTH, false);
    }
  }
  
  private TimeBoardView() { }
}
