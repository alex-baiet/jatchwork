package fr.jatchwork.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represent a piece of patch.
 * 
 * @param shape The user should be able to access a tile using a (x, y)
 *              coordinate like shape.get(x).get(y)
 */
public record Patch(int timeCost, int buttonCost, int buttonIncome, List<List<Boolean>> shape) {
  /** @brief A leather patch, gathered from the timeboard. */
  public static final Patch LEATHER = new Patch(0, 0, 0, "#");
  
  public Patch {
    // Check all parameter validity
    if (timeCost < 0)
      throw new IllegalArgumentException("priceTime should be >= 0.");
    if (buttonCost < 0)
      throw new IllegalArgumentException("priceButton should be >= 0.");
    if (buttonIncome < 0)
      throw new IllegalArgumentException("buttonIncome should be >= 0.");
    Objects.requireNonNull(shape);
    if (shape.size() == 0 || shape.get(0).size() == 0)
      throw new IllegalArgumentException("shape should not be empty.");
    int width = shape.get(0).size();
    for (var line : shape) {
      if (line.size() != width)
        throw new IllegalArgumentException("shape should have all lines of the same size.");
    }
  }

  public Patch(int timeCost, int buttonCost, int buttonIncome, boolean[][] shape) {
    this(timeCost, buttonCost, buttonIncome, toImmutable(shape));
  }

  public Patch(int timeCost, int buttonCost, int buttonIncome, String shape) {
    this(timeCost, buttonCost, buttonIncome, toImmutable(shape));
  }

  /**
   * Return true if a tile in the shape is occuped by the patch
   * 
   * @param x Horizontal position
   * @param y Vertical position
   */
  public boolean getTile(int x, int y) {
    if (x < 0 || y < 0 || x >= shape.size() || y >= shape.get(0).size())
      return false;
    return shape.get(x).get(y);
  }

  /**
   * The horizontal size of a patch.
   */
  public int width() {
    return shape.size();
  }

  /**
   * The vertical size of a patch.
   */
  public int height() {
    return shape.get(0).size();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (int y = 0; y < shape.get(0).size() || y < 3; y++) {
      if (y < shape.get(0).size()) {
        // Draw shape
        for (int x = 0; x < shape.size(); x++) {
          builder.append(shape.get(x).get(y) ? '#' : ' ');
        }
      }
      if (y < 3) {
        builder.append('\t');
        switch (y) {
        case 0 -> builder.append("time cost :     ").append(timeCost);
        case 1 -> builder.append("button cost :   ").append(buttonCost);
        case 2 -> builder.append("button income : ").append(buttonIncome);
        }
      }
      builder.append('\n');
    }

    return builder.toString();
  }

  /**
   * Returns a new patch which is a 90 degree clockwise rotation of this patch.
   * 
   * @return A new patch object representing the 90 degree clockwise rotation of
   *         this patch.
   */
  public Patch rotate() {
    int width = shape.size();
    int height = shape.get(0).size();
    List<List<Boolean>> newShape = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<Boolean> newRow = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        newRow.add(shape.get(j).get(height - i - 1));
      }
      newShape.add(newRow);
    }
    return new Patch(timeCost, buttonCost, buttonIncome, newShape);
  }

  /**
   * Returns a new patch which is a horizontally flipped version of this patch.
   * 
   * @return A new patch object representing the horizontally flipped version of
   *         this patch.
   */
  public Patch flipHorizontally() {
    List<List<Boolean>> newShape = new ArrayList<>();
    for (var row : shape) {
      List<Boolean> newRow = new ArrayList<>(row);
      Collections.reverse(newRow);
      newShape.add(newRow);
    }
    return new Patch(timeCost, buttonCost, buttonIncome, newShape);
  }

  /**
   * Returns a new patch which is a vertically flipped version of this patch.
   * 
   * @return A new patch object representing the vertically flipped version of
   *         this patch.
   */

  public Patch flipVertically() {
    List<List<Boolean>> newShape = new ArrayList<>(shape);
    Collections.reverse(newShape);
    return new Patch(timeCost, buttonCost, buttonIncome, newShape);
  }

  /**
   * Transform a 2D array to an immutable list (used for constructor)
   */
  private static List<List<Boolean>> toImmutable(boolean[][] shape) {
    var result = new ArrayList<List<Boolean>>();
    for (var line : shape) {
      var copy = new ArrayList<Boolean>();
      for (var elem : line) {
        copy.add(elem);
      }
      result.add(Collections.unmodifiableList(copy));
    }
    return Collections.unmodifiableList(result);
  }

  /**
   * Transforme a text to a matrix The text format should look like that
   * (watch declaration for better display) :
   * ##.
   * .##
   * ..#
   */
  private static List<List<Boolean>> toImmutable(String text) {
    var result = new ArrayList<List<Boolean>>();
    var lines = text.split("\n");
    // Construct shape by reading text column by column, not line by line
    for (int x = 0; x < lines[0].length(); x++) {
      var column = new ArrayList<Boolean>();
      for (int y = 0; y < lines.length; y++) {
        column.add(lines[y].charAt(x) == '#' ? true : false);
      }
      result.add(Collections.unmodifiableList(column));
    }
    return Collections.unmodifiableList(result);
  }
}
