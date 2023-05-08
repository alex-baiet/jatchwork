package fr.jatchwork.model;

/**
 * Contains a patch and its coordinate in the quiltboard.
 */
public record PatchCoord(Patch patch, int x, int y) {
  
}