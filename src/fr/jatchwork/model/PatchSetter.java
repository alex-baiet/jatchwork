package fr.jatchwork.model;

import java.util.Objects;

/**
 * Used to place a patch on a quilt board.
 */
public class PatchSetter {
  private final QuiltBoard board;
  private Patch patch;
  private Vector pos;
  
  public PatchSetter(QuiltBoard board, Patch patch) {
    Objects.requireNonNull(board);
    Objects.requireNonNull(patch);
    this.board = board;
    this.patch = patch;
    this.pos = null;
  }
  
  public QuiltBoard board() { return board; }
  
  public Patch patch() { return patch; }
  
  public Vector position() {
    return pos;
  }
  
  /**
   * Change position of the patch on the quilt board
   * @param pos
   */
  public void setPosition(Vector pos) {
    this.pos = pos;
  }
  
  /**
   * The validity of the position of the patch
   * @return True if valid, false otherwise
   */
  public boolean validPosition() {
    return board.fit(patch, pos.x(), pos.y());
  }
  
  /**
   * Rotate the patch
   */
  public void rotate() {
    patch = patch.rotate();
  }
  
  /**
   * Flip the patch
   */
  public void flip() {
    patch = patch.flipHorizontally();
  }
}
