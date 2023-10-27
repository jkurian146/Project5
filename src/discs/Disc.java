package discs;

public interface Disc {

  /**
   * Gets the color of the game disc.
   * @return the color of this GameDisc
   */
  DiscColor getColor();

  /**
   * Checks if this GameDisc is the same as the given Object.
   *
   * @param o the object to compare this GameDisc with
   * @return true if the given object is GameDisc and has the same features as this GameDisc.
   */
  boolean equals(Object o);

/**
 * Returns a hash code value for this GameDisc.
 */
  int hashcode();
}