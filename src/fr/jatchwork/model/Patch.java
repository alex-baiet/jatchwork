package fr.jatchwork.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represent a piece of patch.
 */
public record Patch(int timeCost, int buttonCost, int buttonIncome, List<List<Boolean>> shape) {
  
  public Patch {
    // Check all parameter validity
    if (timeCost < 0) throw new IllegalArgumentException("priceTime should be >= 0.");
    if (buttonCost < 0) throw new IllegalArgumentException("priceButton should be >= 0.");
    if (buttonIncome < 0) throw new IllegalArgumentException("buttonIncome should be >= 0.");
    Objects.requireNonNull(shape);
    if (shape.size() == 0 || shape.get(0).size() == 0) throw new IllegalArgumentException("shape should not be empty.");
    int width = shape.get(0).size();
    for (var line : shape) {
      if (line.size() != width) throw new IllegalArgumentException("shape should have all lines of the same size.");
    }
  }
  
  public Patch(int timeCost, int buttonCost, int buttonIncome, boolean[][] shape) {
    this(timeCost, buttonCost, buttonIncome, toImmutable(shape));
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    for (int i = 0; i < shape.size() || i < 3; i++) {
      if (i < shape.size()) {
        // Draw shape
        for (int j = 0; j < shape.get(i).size(); j++) {
          builder.append(shape.get(i).get(j) ? '#' : ' ');
        }
      }
      if (i < 3) {
        builder.append('\t');
        switch (i) {
          case 0 -> builder.append("time cost :     ").append(timeCost);
          case 1 -> builder.append("button cost :   ").append(buttonCost);
          case 2 -> builder.append("button income : ").append(buttonIncome);
        }
      }
      builder.append('\n');
    }
    
    return builder.toString();
  }

  /**
   * Transform a 2D array to an immutable list (used for constructor)
   */
  private static List<List<Boolean>> toImmutable(boolean[][] shape) {
    var result = new ArrayList<List<Boolean>>();
    for (var line : shape) {
      var copy = new ArrayList<Boolean>();
      for (var elem : line) {
        copy.add(elem);
      }
      result.add(Collections.unmodifiableList(copy));
    }
    return Collections.unmodifiableList(result);
  }

  public static void main(String[] args) {
    // To test patch
    Patch p = new Patch(4, 3, 1, new boolean[][] {
      { true , false },
      { true , true  },
      { true , true  },
      { false, true  },
      { true , true  },
      { true , true  },
    });
    System.out.println(p);
  }

}
