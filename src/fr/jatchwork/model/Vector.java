package fr.jatchwork.model;

import java.util.Objects;

/**
 * A 2D Coordinate used to place patch on the quiltboard.
 * @param x Horizontal position
 * @param y Vertical position
 */
public record Vector(int x, int y) {
  public static final Vector ZERO = new Vector(0, 0);

  /**
   * Make the sum of two vectors.
   * @param v To add
   * @return Sum of vectors
   */
  public Vector add(Vector v) {
    Objects.requireNonNull(v);
    return new Vector(x + v.x, y + v.y);
  }

  /**
   * Make the sum of two vectors.
   * @param x X vector value
   * @param y Y vector value
   * @return Sum of vectors
   */
  public Vector add(int x, int y) {
    return new Vector(this.x + x, this.y + y);
  }

  /**
   * Substract this with given vector.
   * @param v To substract
   * @return Result of substraction
   */
  public Vector sub(Vector v) {
    Objects.requireNonNull(v);
    return new Vector(x - v.x, y - v.y);
  }

  /**
   * Make the product of the vector with n.
   * @param n The multiplier
   * @return The product
   */
  public Vector multiply(float n) {
    return new Vector((int)(x * n), (int)(y * n));
  }
}
