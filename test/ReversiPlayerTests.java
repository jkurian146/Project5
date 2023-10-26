import org.junit.Test;

import model.ReversiHexModel;
import tiles.Tile;

public class ReversiPlayerTests {
  @Test
  public void testSomething1() {
    ReversiHexModel rihm = new ReversiHexModel();
    rihm.startGame(7,7, Tile.HEXTILE);
  }
  @Test
  public void testSomething2() {
    ReversiHexModel rihm = new ReversiHexModel();
    rihm.startGame(11,11, Tile.HEXTILE);
  }
}
