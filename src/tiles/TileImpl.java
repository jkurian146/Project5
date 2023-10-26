package tiles;

import java.util.Objects;

public class TileImpl implements ITile {
  private final Tile type;
  private TileColor tileColor;
  public TileImpl(Tile type, TileColor tileColor) {
    this.type = type;
    this.tileColor = tileColor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    TileImpl other = (TileImpl) o;
    return  other.tileColor == this.tileColor && other.type == this.type;

  }

  @Override
  public int hashcode() {
    return Objects.hash(this.tileColor, this.type);
  }
}
