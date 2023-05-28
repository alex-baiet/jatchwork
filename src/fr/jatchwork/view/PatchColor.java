package fr.jatchwork.view;

import java.awt.Color;
import java.util.Objects;

/**
 * To store a set of color for a patch.
 * @param fillColor Main color
 * @param borderColor External Borders color
 * @param borderInColor Internal borders color
 */
public record PatchColor(Color fillColor, Color borderColor, Color borderInColor) {
  /**
   * Default color in transparent.
   */
  public static final PatchColor TRANSPARENT_COLORS = new PatchColor(
      new Color(128, 128, 128, 128),
      new Color(255, 255, 255, 128),
      new Color(192, 192, 192, 128));

  /**
   * Red colors in transparent.
   */
  public static final PatchColor RED_COLORS = new PatchColor(
      new Color(128, 0, 0, 128),
      new Color(255, 0, 0, 128),
      new Color(192, 0, 0, 128));

  /**
   * Create a new PatchColor.
   * @param fillColor The main color
   * @param borderColor External borders color
   * @param borderInColor Internal borders color
   */
  public PatchColor {
    Objects.requireNonNull(fillColor);
    Objects.requireNonNull(borderColor);
    Objects.requireNonNull(borderInColor);
  }
}
