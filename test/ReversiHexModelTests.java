import org.junit.Test;
import model.ReversiHexModel;

public class ReversiHexModelTests {




  @Test
  public void testSomething1() {
    ReversiHexModel rihm = new ReversiHexModel();
    rihm.startGame(7);
  }
  @Test
  public void testSomething2() {
    ReversiHexModel rihm = new ReversiHexModel();
    rihm.startGame(11);
  }
}