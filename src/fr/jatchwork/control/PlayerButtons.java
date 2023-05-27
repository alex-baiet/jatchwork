package fr.jatchwork.control;

import java.util.Objects;

public record PlayerButtons(Button rotate, Button flip, Button buy) {
  public PlayerButtons {
    Objects.requireNonNull(rotate);
    Objects.requireNonNull(flip);
    Objects.requireNonNull(buy);
  }
}
