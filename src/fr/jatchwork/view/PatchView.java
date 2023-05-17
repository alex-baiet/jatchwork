package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.jatchwork.model.Patch;
import fr.jatchwork.model.Vector;

final class PatchView {
  private static final Color COLOR = Color.GRAY;
  private static final Color BORDER = Color.WHITE;
  private static final int BORDER_SIZE = 4;

  private PatchView() { }

  /**
   * Draw a patch on the window.
   * @param graphics Window's graphics
   * @param patch What to draw
   * @param pos Where to draw the top left corner, border excluded
   */
  public static void drawPatch(Graphics2D graphics, Patch patch, Vector pos) {
    graphics.setColor(COLOR);
    int square = ViewWindow.squareSize();

    // Draw main color
    for (int x = 0; x < patch.width(); x++) {
      for (int y = 0; y < patch.height(); y++) {
        if (patch.getTile(x, y)) {
          graphics.fillRect(
              pos.x() + x * square,
              pos.y() + y * square,
              square,
              square);
        }
      }
    }

    // Draw borders
    for (int x = 0; x <= patch.width(); x++) {
      for (int y = 0; y <= patch.height(); y++) {
        // Horizontal borders
        if (x < patch.width() && patch.getTile(x, y - 1) ^ patch.getTile(x, y)) {
          HelpWindow.drawLine(graphics, BORDER, pos.add(x * square, y * square), square, BORDER_SIZE, true);
        }
        // Vertical borders
        if (y < patch.height() && patch.getTile(x - 1, y) ^ patch.getTile(x, y)) {
          HelpWindow.drawLine(graphics, BORDER, pos.add(x * square, y * square), square, BORDER_SIZE, false);
        }
      }
    }
  }

  /**
   * Draw a patch on the window, centered on given position.
   * @param graphics Window's graphics
   * @param patch What to draw
   * @param pos Where to draw the center
   */
  public static void drawPatchCenter(Graphics2D graphics, Patch patch, Vector pos) {
    int square = ViewWindow.squareSize();
    drawPatch(
        graphics,
        patch,
        pos.add(-square * patch.width() / 2, -square * patch.height() / 2));
  }
}
