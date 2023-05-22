package fr.jatchwork.control;

public class PlayerButtons {
  private final Button rotate;
  private final Button flip;
  private final Button buy;

  public PlayerButtons(Button rotate, Button flip, Button buy) {
    this.rotate = rotate;
    this.flip = flip;
    this.buy = buy;
  }

  public Button rotate() { return rotate; }
  public Button flip() { return flip; }
  public Button buy() { return buy; }
}
