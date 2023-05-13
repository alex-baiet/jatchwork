package fr.jatchwork.model;

/**
 * A 2D Coordinate used to place patch on the quiltboard.
 * @param x Horizontal position
 * @param y Vertical position
 */
public record Vector(int x, int y) {

  /**
   * Make the sum of two vectors.
   * @param v To add
   * @return Sum of vectors
   */
  public Vector add(Vector v) {
    return new Vector(x + v.x, y + v.y);
  }

  /**
   * Make the sum of two vectors.
   * @param x
   * @param y
   * @return Sum of vectors
   */
  public Vector add(int x, int y) {
    return new Vector(this.x + x, this.y + y);
  }
  
  /**
   * Make the product of the vector with n.
   * @param n The multiplier
   * @return The product
   */
  public Vector multiply(int n) {
    return new Vector(x * n, y * n);
  }
}
