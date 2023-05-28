package fr.jatchwork.control;

import java.util.Objects;

/**
 * Buttons to rotate, flip and buy a patch for a single player.
 * @param rotate Button to rotate the selected patch
 * @param flip Button to flip the selected patch
 * @param buy Button to buy the selected patch
 */
public record PlayerButtons(Button rotate, Button flip, Button buy) {
  /**
   * Create a new object to store player's buttons.
   * @param rotate Button to rotate the selected patch
   * @param flip Button to flip the selected patch
   * @param buy Button to buy the selected patch
   */
  public PlayerButtons {
    Objects.requireNonNull(rotate);
    Objects.requireNonNull(flip);
    Objects.requireNonNull(buy);
  }
}
