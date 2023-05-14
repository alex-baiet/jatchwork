package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.TimeBoard;
import fr.jatchwork.model.Vector;

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
  }
  
  /**
   * Get the position of a square.
   * @param basePos Top left corner of the time board
   * @param i Index of square
   * @return Top left corner of the square in pixel
   */
  private static Vector squarePos(Vector basePos, int i) {
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

  /**
   * Get the position between given square and the previous one.
   * @param basePos 
   * @param i
   * @return Centered position on the line between the two squares
   */
  private static Vector getBetweenSquarePos(Vector basePos, int i) {
    int square = ViewWindow.squareSize();
    return squarePos(basePos, i).add(squarePos(basePos, i-1)).add(square, square).multiply(0.5f);
  }

  private TimeBoardView() { }
}
