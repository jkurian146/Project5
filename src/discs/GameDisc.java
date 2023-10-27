package discs;

import java.util.Objects;

// find better name.
public class GameDisc implements Disc {
  private final DiscType type;
  private DiscColor color;
  public GameDisc(DiscType type, DiscColor tileColor) {
    this.type = type;
    this.color = tileColor;
  }

  @Override
  public DiscColor getColor() {
    return this.color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    GameDisc other = (GameDisc) o;
    return  other.color == this.color && other.type == this.type;

  }

  @Override
  public int hashcode() {
    return Objects.hash(this.color, this.type);
  }
}