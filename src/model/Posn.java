package model;

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

}
