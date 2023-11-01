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
    // move left
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
    // move right
    model.makeMove(5,4);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(5,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(1,4).getColor());

  }

  @Test
  public void integrationMoves() {
    model.startGame(7);

    //   0 1 2 3 4 5 6                 0 1 2 3 4 5 6
    // 0 n n _ _ _ _ n               0    _ _ _ _
    // 1 n _ _ _ _ _ n               1   _ _ _ _ _
    // 2 n _ _ x o _ _               2  _ _ x o _ _
    // 3 _ _ o _ x _ _               3 _ _ o _ x _ _
    // 4 n _ _ x o _ _               4  _ _ x o _ _
    // 5 n _ _ _ _ _ n               5   _ _ _ _ _
    // 6 n n _ _ _ _ n               6    _ _ _ _

    model.pass();
    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());
    model.makeMove(2,2);

    //   0 1 2 3 4 5 6                 0 1 2 3 4 5 6
    // 0 n n _ _ _ _ n               0    _ _ _ _
    // 1 n _ _ _ _ _ n               1   _ _ _ _ _
    // 2 n _ x x o _ _               2  _ x x o _ _
    // 3 _ _ x _ x _ _               3 _ _ x _ x _ _
    // 4 n _ _ x o _ _               4  _ _ x o _ _
    // 5 n _ _ _ _ _ n               5   _ _ _ _ _
    // 6 n n _ _ _ _ n               6    _ _ _ _

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2,2).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 3).getColor());

    model.makeMove(5,2);

    //   0 1 2 3 4 5 6                 0 1 2 3 4 5 6
    // 0 n n _ _ _ _ n               0    _ _ _ _
    // 1 n _ _ _ _ _ n               1   _ _ _ _ _
    // 2 n _ x x o o _               2  _ x x o o _
    // 3 _ _ x _ o _ _               3 _ _ x _ o _ _
    // 4 n _ _ x o _ _               4  _ _ x o _ _
    // 5 n _ _ _ _ _ n               5   _ _ _ _ _
    // 6 n n _ _ _ _ n               6    _ _ _ _

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(5,2).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,3).getColor());


    model.makeMove(6,2);

    //   0 1 2 3 4 5 6                 0 1 2 3 4 5 6
    // 0 n n _ _ _ _ n               0    _ _ _ _
    // 1 n _ _ _ _ _ n               1   _ _ _ _ _
    // 2 n _ x x x x x               2  _ x x x x x
    // 3 _ _ x _ o _ _               3 _ _ x _ o _ _
    // 4 n _ _ x o _ _               4  _ _ x o _ _
    // 5 n _ _ _ _ _ n               5   _ _ _ _ _
    // 6 n n _ _ _ _ n               6    _ _ _ _

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(6,2).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(5,2).getColor());

    model.pass();
    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());
    model.makeMove(5, 4);

    //   0 1 2 3 4 5 6                 0 1 2 3 4 5 6
    // 0 n n _ _ _ _ n               0    _ _ _ _
    // 1 n _ _ _ _ _ n               1   _ _ _ _ _
    // 2 n _ x x x x x               2  _ x x x x x
    // 3 _ _ x _ x _ _               3 _ _ x _ x _ _
    // 4 n _ _ x x x _               4  _ _ x x x _
    // 5 n _ _ _ _ _ n               5   _ _ _ _ _
    // 6 n n _ _ _ _ n               6    _ _ _ _

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(5,4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,3).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,4).getColor());
    Assert.assertTrue(model.isGameOver());
  }
}