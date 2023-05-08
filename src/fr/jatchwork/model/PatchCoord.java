package fr.jatchwork.model;

/**
 * Contains a patch and its coordinate in the quilt board.
 * @param patch The patch
 * @param x Horizontal position
 * @param y Vertical position
 */
public record PatchCoord(Patch patch, int x, int y) {
  
}