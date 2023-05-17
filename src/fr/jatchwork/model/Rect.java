package fr.jatchwork.model;

public record Rect(int x, int y, int width, int height) {
  public Vector pos() { return new Vector(x, y); }
  public Vector size() { return new Vector(width, height); }
}
