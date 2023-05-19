package fr.jatchwork.model;

import java.util.Objects;

/**
 * Represent a rectangle.
 * @param x X position
 * @param y Y position
 * @param width Width
 * @param height Height
 */
public record Rect(int x, int y, int width, int height) {
  /**
   * Position of the rectangle.
   * @return X and Y position
   */
  public Vector pos() { return new Vector(x, y); }
  
  /**
   * Size of the rectangle.
   * @return Width and height of the rectangle
   */
  public Vector size() { return new Vector(width, height); }
  
  /**
   * Verify that the given position is inside the rect.
   * @param pos Position to test
   * @return True if inside, false if not
   */
  public boolean isInside(Vector pos) {
    Objects.requireNonNull(pos);
    return pos.x() >= x
        && pos.y() >= y
        && pos.x() < x + width
        && pos.y() < y + height;
  }
}
