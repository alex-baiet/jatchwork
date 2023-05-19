package fr.jatchwork.model;

import java.util.Objects;

/**
 * Contains a patch and its coordinate in the quilt board.
 * @param patch The patch
 * @param x Horizontal position
 * @param y Vertical position
 */
public record PatchCoord(Patch patch, Vector pos) {
  public PatchCoord {
    Objects.requireNonNull(patch);
    Objects.requireNonNull(pos);
  }
}