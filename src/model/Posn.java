package model;

import java.util.Objects;

import discs.GameDisc;

public class Posn {
  private int x;
  private int y;

  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Checks if this GameDisc is the same as the given Object.
   *
   * @param o the object to compare this GameDisc with
   * @return true if the given object is GameDisc and has the same features as this GameDisc.
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
   Posn other = (Posn) o;
    return  other.x == this.x && other.y == this.y;

  }


  /**
   * Returns a hash code value for this GameDisc.
   */
  public int hashcode() {
    return Objects.hash(this.x, this.y);
  }

}
