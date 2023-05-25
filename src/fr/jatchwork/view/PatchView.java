package fr.jatchwork.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import fr.jatchwork.model.Patch;
import fr.jatchwork.model.Rect;
import fr.jatchwork.model.Vector;

/**
 * Allow construction of a patch visual and its drawing.
 */
final class PatchView {
  private static final int BORDER_SIZE = 4;
  private static final int BORDER_IN_SIZE = 2;
  
  private final Patch patch;
  private Vector pos;
  private Color fillColor = Color.GRAY;
  private Color borderColor = Color.WHITE;
  private Color borderInColor = Color.LIGHT_GRAY;

  /**
   * Create a new patch.
   * Position must be set once before call to draw().
   * @param patch The patch to draw
   */
  public PatchView(Patch patch) {
    this(patch, null);
  }

  /**
   * Create a new patch.
   * @param patch Patch to draw.
   * @param pos
   */
  public PatchView(Patch patch, Vector pos) {
    this.patch = patch;
    this.pos = pos;
  }

  /**
   * Set the position in the window.
   * @param pos Where to draw
   */
  public void setPosition(Vector pos) {
    Objects.requireNonNull(pos);
    this.pos = pos;
  }
  
  /**
   * Set the position, centered in given rectangle.
   * @param rect Where to center
   */
  public void centerPosition(Rect rect) {
    Objects.requireNonNull(rect);
    pos = rect.pos()
      .add(
          rect.size().sub(
              patch.size().multiply(
                  ViewWindow.squareSize()
              )
          ).multiply(
              0.5f
          )
      );
  }
  
  /**
   * Set the main color of the patch.
   * @param color Color
   */
  public void setFillColor(Color color) {
    Objects.requireNonNull(color);

  }
  
  /**
   * Set the border color.
   * @param color Color
   */
  public void setBorderColor(Color color) {
    Objects.requireNonNull(color);

  }

  /**
   * Set the border color inside.
   * @param color Color
   */
  public void setBorderInColor(Color color) {
    Objects.requireNonNull(color);

  }
  
  /**
   * Draw the patch.
   * @param graphics Window's graphics
   */
  public void draw(Graphics2D graphics) {
    Objects.requireNonNull(graphics);
    if (pos == null) throw new IllegalStateException("setPosition must be called once before draw.");

    graphics.setColor(fillColor);
    int square = ViewWindow.squareSize();

    drawFill(graphics, square);
    drawBorderIn(graphics, square);
    drawBorderOut(graphics, square);
  }

  /**
   * Draw the main color.
   * @param graphics Window's graphics
   * @param square Size of each square of the patch
   */
  private void drawFill(Graphics2D graphics, int square) {
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
  }
  
  /**
   * Draw the borders inside.
   * @param graphics Window's graphics
   * @param square Size of each square of the patch
   */
  private void drawBorderIn(Graphics2D graphics, int square) {
    for (int x = 0; x <= patch.width(); x++) {
      for (int y = 0; y <= patch.height(); y++) {
        final Vector v = pos.add(x * square, y * square);
        // Horizontal borders
        if (x < patch.width() && patch.getTile(x, y - 1) && patch.getTile(x, y)) {
          HelpWindow.drawLine(graphics, borderInColor, v, square, BORDER_IN_SIZE, true);
        }
        // Vertical borders
        if (y < patch.height() && patch.getTile(x - 1, y) && patch.getTile(x, y)) {
            HelpWindow.drawLine(graphics, borderInColor, v, square, BORDER_IN_SIZE, false);
        }
      }
    }

  }
  
  /**
   * Draw the external borders.
   * @param graphics Window's graphics
   * @param square Size of each square of the patch
   */
  private void drawBorderOut(Graphics2D graphics, int square) {
    for (int x = 0; x <= patch.width(); x++) {
      for (int y = 0; y <= patch.height(); y++) {
        final Vector v = pos.add(x * square, y * square);
        // Horizontal borders
        if (x < patch.width() && patch.getTile(x, y - 1) ^ patch.getTile(x, y)) {
          HelpWindow.drawLine(graphics, borderColor, v, square, BORDER_SIZE, true);
        }
        // Vertical borders
        if (y < patch.height() && patch.getTile(x - 1, y) ^ patch.getTile(x, y)) {
            HelpWindow.drawLine(graphics, borderColor, v, square, BORDER_SIZE, false);
        }
      }
    }
  }
  
}
