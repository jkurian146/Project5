import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import discs.Disc;
import discs.DiscColor;
import discs.DiscType;
import discs.GameDisc;
import model.MockReversiHexModel;
import model.ReversiHexModel;
import model.ReversiModel;
import player.PlayerTurn;
import view.ReversiTextualView;
import view.TextualView;

public class ReversiHexModelTests {
  ReversiModel model;
  PlayerTurn player1;
  PlayerTurn player2;
  ReversiTextualView textualView;
  @Before
  public void initData() {
    this.model = new ReversiHexModel();
    this.player1 = PlayerTurn.PLAYER1;
    this.player2 = PlayerTurn.PLAYER2;
    this.textualView = new TextualView(model, new StringBuilder());

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
    Assert.assertThrows(IllegalStateException.class, () -> model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> model.currentTurn());
    Assert.assertThrows(IllegalStateException.class, () -> model.getDiscAt(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> model.isDiscFlipped(0, 0));
    Assert.assertThrows(IllegalStateException.class, () -> model.pass());
  }

  @Test
  public void testStartGameTwice() {
    model.startGame(7);
    Assert.assertThrows(IllegalStateException.class, () ->  model.startGame(7));
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

  // negative tests
  @Test
  public void testMoveToMiddle() {
    model.startGame(7);
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(3,3));
  }

  @Test
  public void testMoveToNonFlipped() {
    model.startGame(7);
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(4,3));
  }

  @Test
  public void testMoveNotJumpingEmpty() {
    model.startGame(7);
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(4,1));
  }

  @Test
  public void testUserProvidedInvalidCoordinate() {
    model.startGame(7);
    Assert.assertThrows(IllegalArgumentException.class, () -> model.makeMove(6,0));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.makeMove(1,0));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.makeMove(6,6));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.makeMove(1,6));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.makeMove(-1,1));
  }

  @Test
  public void integrationMoves() {
    model.startGame(7);
    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());

    model.makeMove(2,2);
    textualView.render();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2,2).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 3).getColor());

    model.makeMove(5,2);
    textualView.render();

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(5,2).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(4,3).getColor());


    model.makeMove(6,2);
    textualView.render();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(6,2).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(5,2).getColor());

    model.pass();
    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());
    model.makeMove(5, 4);
    textualView.render();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(5,4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,3).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,4).getColor());
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testIsGameOverWhenPassedTwice() {
    model.startGame(7);
    model.pass();
    model.pass();
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testIsGameOverWhenNotPassedTwice() {
    model.startGame(7);
    model.pass();
    model.makeMove(5,2);
    model.pass();
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  public void testGameIsOverWhenNoValidMovesForEither() {
    Disc[][] mockBoard = new Disc[5][5];
    mockBoard[0][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[1][2] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[2][2] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[3][1] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[4][1] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    ReversiModel mockModel = new MockReversiHexModel(mockBoard);
    mockModel.startGame(5);
    Assert.assertTrue(mockModel.isGameOver());
  }

  @Test
  public void testGameIsOverBoardIsFull() {
    Disc[][] mockBoard = new Disc[5][5];
    mockBoard[0][1] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[0][2] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[0][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[1][0] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[1][1] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[1][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[2][0] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[2][1] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[2][2] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[2][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[2][4] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[3][0] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[3][1] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[3][2] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[3][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[4][1] = new GameDisc(DiscType.HEXDISC,DiscColor.WHITE);
    mockBoard[4][2] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);
    mockBoard[4][3] = new GameDisc(DiscType.HEXDISC,DiscColor.BLACK);

    ReversiModel mockModel = new MockReversiHexModel(mockBoard);
    mockModel.startGame(5);
    Assert.assertTrue(mockModel.isGameOver());
  }

  @Test
  public void testViewForSizes() {
    model.startGame(11);
    Assert.assertEquals("     - - - - - -     \n" +
            "    - - - - - - -    \n" +
            "   - - - - - - - -   \n" +
            "  - - - - - - - - -  \n" +
            " - - - - X O - - - - \n" +
            "- - - - O - X - - - -\n" +
            " - - - - X O - - - - \n" +
            "  - - - - - - - - -  \n" +
            "   - - - - - - - -   \n" +
            "    - - - - - - -    \n" +
            "     - - - - - -     \n", textualView.toString());

  }
}