import org.junit.Assert;
import org.junit.Test;

import discs.GameDisc;
import model.Posn;
import model.ReversiHexModel;

public class ReversiTestGettersOnBoard {
  @Test
  public void testGetDiscAt() {
    ReversiHexModel rhm = new ReversiHexModel();
    rhm.startGame(5);
    Assert.assertThrows(IllegalArgumentException.class, () -> rhm.getDiscAt(new Posn(0,3)));
    Assert.assertThrows(IllegalArgumentException.class, () -> rhm.getDiscAt(new Posn(1,4)));
    Assert.assertThrows(IllegalArgumentException.class, () -> rhm.getDiscAt(new Posn(2,5)));
    Assert.assertThrows(IllegalArgumentException.class, () -> rhm.getDiscAt(new Posn(6,6)));
    Assert.assertThrows(IllegalArgumentException.class, () -> rhm.getDiscAt(new Posn(-1,-1)));
    Assert.assertTrue(rhm.getDiscAt(new Posn(0,2)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(0,0)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(4,0)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(4,2)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(3,3)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(3,1)) instanceof GameDisc);
    Assert.assertTrue(rhm.getDiscAt(new Posn(4,2)) instanceof GameDisc);
  }
}
