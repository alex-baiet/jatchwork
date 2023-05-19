package fr.jatchwork.model;

import java.util.Objects;

/**
 * Contains a patch and its coordinate in the quilt board.
 * @param patch The patch
 * @param pos Position
 */
public record PatchCoord(Patch patch, Vector pos) {
  /**
   * Create a new PatchCoord.
   * @param patch The patch
   * @param pos Position
   */
  public PatchCoord {
    Objects.requireNonNull(patch);
    Objects.requireNonNull(pos);
  }
}