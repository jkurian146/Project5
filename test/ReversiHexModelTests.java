import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import discs.DiscColor;
import model.ReversiHexModel;
import model.ReversiModel;
import player.PlayerTurn;

public class ReversiHexModelTests {
  ReversiModel model;
  PlayerTurn player1;
  PlayerTurn player2;

  @Before
  public void initData() {
    this.model = new ReversiHexModel();
    this.player1 = PlayerTurn.PLAYER1;
    this.player2 = PlayerTurn.PLAYER2;
  }


  @Test
  public void testStartGameWithInvalidBoardSizes() {
    int lessThan3 = 1;
    int three = 3;
    int evenNumber = 6;

    // Assert that starting the game with invalid board sizes throw the expected exceptions
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(lessThan3));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(three));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(evenNumber));

    // Assert that attempting to start the game after
    // it has already been started throws an exception
    model.startGame(7);
    Assert.assertThrows(IllegalStateException.class, () -> model.startGame(7));
  }

  @Test
  public void testAttemptToDoSomethingWhileGameNotStarted() {
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(0, 0));
//    Assert.assertThrows(IllegalStateException.class, () -> model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> model.currentTurn());
    Assert.assertThrows(IllegalStateException.class,
            () -> model.getPlayerScore(model.currentTurn()));
    Assert.assertThrows(IllegalStateException.class, () -> model.getDiscAt(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> model.isDiscFlipped(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> model.pass());
  }

  @Test
  public void testCurrentTurnTogglePlayerAndPass() {
    model.startGame(7);

    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
    model.pass();
    Assert.assertEquals(player1, model.currentTurn());
    model.pass();
    Assert.assertEquals(player2, model.currentTurn());
  }

  @Test
  public void testSimpleMoves() {
    model.startGame(7);
    model.pass();
    // up right
    model.makeMove(3,1);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2,3).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3,2).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3,1).getColor());
    // up left
    model.makeMove(3,0);
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,0).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,1).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,2).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,3).getColor());
    model.pass();
    // down left
    model.makeMove(2,4);
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2,4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2,3).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,2).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,1).getColor());
    // regular left
    model.makeMove(1,4);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(1,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,4).getColor());
    // down right
    model.makeMove(3,5);
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2,3).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,5).getColor());
    // regular right
    model.makeMove(5,4);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(5,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(1,4).getColor());

  }

}


























