package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import fr.jatchwork.model.PatchCoord;
import fr.jatchwork.model.PatchSetter;
import fr.jatchwork.model.QuiltBoard;
import fr.jatchwork.model.Vector;

/**
 * Contains methods to manage visually quilt boards.
 */
final class QuiltBoardView {
  private static final int BORDER_OUT_SIZE = 4;
  private static final int BORDER_IN_SIZE = 2;
  private static final Color BORDER_COLOR = Color.WHITE;
  private static final Color BORDER_IN_COLOR = Color.GRAY;
  private static final Color MAIN_COLOR = Color.DARK_GRAY;
  
  /**
   * Get the size of a board on the screen
   * @param board
   * @return Size in pixels
   */
  public static Vector size(QuiltBoard board) {
    Objects.requireNonNull(board);
    int size = board.size() * ViewWindow.squareSize();
    return new Vector(size, size);
  }

  /**
   * Draw a quilt board on the window
   * @param graphics Window's graphics
   * @param board What to draw
   * @param pos Where to draw the top left corner
   * @param patchSetter Patch not definitively placed,
   * Used to view if it can be placed at a position. Can be null
   */
  public static void drawQuiltBoard(Graphics2D graphics, QuiltBoard board, Vector pos, PatchSetter patchSetter) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(board);
    Objects.requireNonNull(pos);
    // Fill main color
    int square = ViewWindow.squareSize();
    int size = board.size() * square;
    graphics.setColor(MAIN_COLOR);
    graphics.fillRect(pos.x(), pos.y(), size, size);

    // Draw inside borders
    for (int i = 1; i < board.size(); i++) {
      drawTwoBorder(graphics, pos, i, size, BORDER_IN_SIZE, BORDER_IN_COLOR);
    }

    // Draw outside borders
    drawTwoBorder(graphics, pos, 0, size, BORDER_OUT_SIZE, BORDER_COLOR); // top and left
    drawTwoBorder(graphics, pos, board.size(), size, BORDER_OUT_SIZE, BORDER_COLOR); // bottom and right

    // Draw contained patches
    for (PatchCoord patchCoord : board.patches()) {
      PatchView.drawPatch(graphics, patchCoord.patch(), pos.add(patchCoord.pos().multiply(square)));
    }
    
    // Draw selected patch
    if (patchSetter != null && patchSetter.position() != null) {
      PatchView.drawPatch(
          graphics,
          patchSetter.patch(),
          pos.add(patchSetter.position().multiply(square)));
    }
  }

  /**
   * Draw both horizontal and vertical borders at given position.
   * @param graphics Window's graphics
   * @param pos The position of the quilt board (not the borders)
   * @param squarePos The position inside the square, from top left corner
   * @param length Length of the border, equals size of the quilt board
   * @param width Stroke width
   */
  private static void drawTwoBorder(Graphics2D graphics, Vector pos, int squarePos, int length, int width, Color color) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(pos);
    int square = ViewWindow.squareSize();
    HelpWindow.drawLine(graphics, color, pos.add(0, squarePos * square), length, width, true);
    HelpWindow.drawLine(graphics, color, pos.add(squarePos * square, 0), length, width, false);
  }

  private QuiltBoardView() { }
}
