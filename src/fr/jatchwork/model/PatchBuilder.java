package fr.jatchwork.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Construct patches by giving arguments one per one, or by reading a file.
 */
public final class PatchBuilder {
  /**
   * Extract a list of patches from a file.
   * @param path Path of the file to read
   * @return List of extracted patches
   * @throws IOException Error when reading the file
   */
  public static List<Patch> fromFile(Path path) throws IOException {
    Objects.requireNonNull(path);
    final var patches = new ArrayList<Patch>();
    Patch patch;
    
    try (var reader = Files.newBufferedReader(path)) {
      while ((patch = nextPatch(reader)) != null) {
        patches.add(patch);
      }
    }
    
    return patches;
  }

  /**
   * Read the next patch from a file
   * @param reader The file to read
   * @return Read patch
   * @throws IOException Error when reading the file
   */
  private static Patch nextPatch(BufferedReader reader) throws IOException {
    Objects.requireNonNull(reader);
    String line = reader.readLine();
    if (line == null) return null;

    final var builder = new PatchBuilder();
    String[] args = line.split(",");
    builder.setTimeCost(Integer.parseInt(args[0]));
    builder.setButtonCost(Integer.parseInt(args[1]));
    builder.setButtonIncome(Integer.parseInt(args[2]));

    final var joiner = new StringJoiner("\n");
    for (line = reader.readLine(); line != null && line.length() > 0; line = reader.readLine()) {
      joiner.add(line);
    }
    builder.setShape(joiner.toString());
    return builder.toPatch();
  }

  private int timeCost;
  private int buttonCost;
  private int buttonIncome;
  private String shape;

  /**
   * Create a new patch builder.
   */
  public PatchBuilder() { }

  /**
   * Set time cost.
   * @param timeCost Time cost
   */
  public void setTimeCost(int timeCost) { this.timeCost = timeCost; }

  /**
   * Set button cost.
   * @param buttonCost button cost
   */
  public void setButtonCost(int buttonCost) { this.buttonCost = buttonCost; }

  /**
   * Set button income.
   * @param buttonIncome button income
   */
  public void setButtonIncome(int buttonIncome) { this.buttonIncome = buttonIncome; }

  /**
   * Set the shape of the patch.
   * @param shape Patch's shape
   */
  public void setShape(String shape) {
    Objects.requireNonNull(shape);
    this.shape = shape;
  }

  /**
   * Generate the patch from previous given values.
   * @return generated patch
   */
  public Patch toPatch() {
    return new Patch(timeCost, buttonCost, buttonIncome, shape);
  }
}
