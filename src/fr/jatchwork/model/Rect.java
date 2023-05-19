package fr.jatchwork.model;

public record Rect(int x, int y, int width, int height) {
  public Vector pos() { return new Vector(x, y); }
  
  public Vector size() { return new Vector(width, height); }
  
  /**
   * Verify that the given position is inside the rect.
   * @param pos Position to test
   * @return True if inside, false if not
   */
  public boolean isInside(Vector pos) {
    return pos.x() >= x
        && pos.y() >= y
        && pos.x() < x + width
        && pos.y() < y + height;
  }
}
