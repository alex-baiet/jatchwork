package fr.jatchwork.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represent a piece of patch.
 */
public record Patch(int timeCost, int buttonCost, int buttonIncome, boolean[][] shape) {
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
  
  public Patch {
    // Check all parameter validity
    if (timeCost < 0) throw new IllegalArgumentException("priceTime should be >= 0.");
    if (buttonCost < 0) throw new IllegalArgumentException("priceButton should be >= 0.");
    if (buttonIncome < 0) throw new IllegalArgumentException("buttonIncome should be >= 0.");
    Objects.requireNonNull(shape);
    if (shape.length == 0 || shape[0].length == 0) throw new IllegalArgumentException("shape should not be empty.");
    int width = shape[0].length;
    for (boolean[] line : shape) {
      if (line.length != width) throw new IllegalArgumentException("shape should have all lines of the same size.");
    }
    
    // Copy shape for security
    shape = Arrays.copyOf(shape, shape.length);
    for (int i = 0; i < shape.length; i++) {
      shape[i] = Arrays.copyOf(shape[i], shape[i].length);
    }
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    for (int i = 0; i < shape.length || i < 3; i++) {
      if (i < shape.length) {
        // Draw shape
        for (int j = 0; j < shape[i].length; j++) {
          builder.append(shape[i][j] ? '#' : ' ');
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
}
