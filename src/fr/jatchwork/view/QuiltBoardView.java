package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.PatchCoord;
import fr.jatchwork.model.QuiltBoard;
import fr.jatchwork.model.Vector;

public class QuiltBoardView {
  private static final int BORDER_OUT_SIZE = 4;
  private static final int BORDER_IN_SIZE = 2;
  private static final Color BORDER_COLOR = Color.WHITE;
  private static final Color MAIN_COLOR = Color.DARK_GRAY;
  
  /**
   * Draw a quilt board on the window
   * @param graphics Window's graphics
   * @param board What to draw
   * @param pos Where to draw the top left corner
   */
  public static void drawQuiltBoard(Graphics2D graphics, QuiltBoard board, Vector pos) {
    // Fill main color
    int square = ViewWindow.squareSize();
    int size = board.size() * square;
    graphics.setColor(MAIN_COLOR);
    graphics.fillRect(pos.x(), pos.y(), size, size);
    
    // Draw outside borders
    graphics.setColor(BORDER_COLOR);
    drawTwoBorder(graphics, pos, 0, size, BORDER_OUT_SIZE); // top and left
    drawTwoBorder(graphics, pos, board.size(), size, BORDER_OUT_SIZE); // bottom and right
    
    // Draw inside borders
    for (int i = 0; i < board.size(); i++) {
      drawTwoBorder(graphics, pos, i, size, BORDER_IN_SIZE);
    }
    
    // Draw contained patches
    for (PatchCoord patchCoord : board.patches()) {
      PatchView.drawPatch(graphics, patchCoord.patch(), pos.add(patchCoord.pos().multiply(square)));
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
  private static void drawTwoBorder(Graphics2D graphics, Vector pos, int squarePos, int length, int width) {
    int square = ViewWindow.squareSize();
    // Draw horizontal border
    graphics.fillRect(
        pos.x() - width / 2,
        pos.y() + squarePos * square - width / 2,
        length + width,
        width);

    // Draw vertical border
    graphics.fillRect(
        pos.x() + squarePos * square - width / 2,
        pos.y() - width / 2,
        width,
        length + width);
  }

  private QuiltBoardView() { }
}
