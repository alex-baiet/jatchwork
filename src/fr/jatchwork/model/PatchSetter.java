package fr.jatchwork.model;

import java.util.Objects;

/**
 * Used to place a patch on a quilt board.
 */
public class PatchSetter {
  private final QuiltBoard board;
  private Patch patch;
  private Vector pos;
  private final int patchNum;
  
  /**
   * Create a new patch setter.
   * @param board Where to place the patch
   * @param patchNum Number of the patch in the game list
   */
  public PatchSetter(QuiltBoard board, int patchNum) {
    Objects.requireNonNull(board);
    this.board = board;
    this.patchNum = patchNum;
    this.patch = Game.instance().getPatch(patchNum);
    this.pos = null;
  }
  
  public PatchSetter(QuiltBoard board, Patch patch) {
    Objects.requireNonNull(board);
    Objects.requireNonNull(patch);
    this.board = board;
    this.patchNum = -1;
    this.patch = patch;
    this.pos = null;
  }
  
  /**
   * Where to place the patch.
   * @return Quilt board
   */
  public QuiltBoard board() { return board; }
  
  /**
   * The patch to place.
   * @return Patch
   */
  public Patch patch() { return patch; }
  
  /**
   * Index of the patch in the game patch list.
   * @return Patch number
   */
  public int patchNum() { return patchNum; }
  
  /**
   * The current position of the patch on the quilt board.
   * @return Position
   */
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
