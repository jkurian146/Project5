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
  public void testIsDiscFlippedValidAndInvalidCoordinates() {
    model.startGame(7);

    Assert.assertFalse(model.isDiscFlipped(0, 0));
    Assert.assertFalse(model.isDiscFlipped(0, 1));
    Assert.assertFalse(model.isDiscFlipped(0, 2));
    Assert.assertFalse(model.isDiscFlipped(0, 3));
    Assert.assertFalse(model.isDiscFlipped(0, 4));
    Assert.assertFalse(model.isDiscFlipped(0, 5));
    Assert.assertFalse(model.isDiscFlipped(0, 6));
    Assert.assertFalse(model.isDiscFlipped(1, 0));
    Assert.assertFalse(model.isDiscFlipped(1, 1));
    Assert.assertFalse(model.isDiscFlipped(1, 2));
    Assert.assertFalse(model.isDiscFlipped(1, 3));
    Assert.assertFalse(model.isDiscFlipped(1, 4));
    Assert.assertFalse(model.isDiscFlipped(1, 5));
    Assert.assertFalse(model.isDiscFlipped(1, 6));
    Assert.assertFalse(model.isDiscFlipped(2, 0));
    Assert.assertFalse(model.isDiscFlipped(2, 1));
    Assert.assertFalse(model.isDiscFlipped(2, 5));
    Assert.assertFalse(model.isDiscFlipped(2, 6));
    Assert.assertFalse(model.isDiscFlipped(3, 0));
    Assert.assertFalse(model.isDiscFlipped(3, 1));
    Assert.assertFalse(model.isDiscFlipped(2, 5));
    Assert.assertFalse(model.isDiscFlipped(2, 6));
    Assert.assertFalse(model.isDiscFlipped(4, 1));
    Assert.assertFalse(model.isDiscFlipped(4, 2));
    Assert.assertFalse(model.isDiscFlipped(4, 4));
    Assert.assertFalse(model.isDiscFlipped(4, 5));
    Assert.assertFalse(model.isDiscFlipped(5, 2));
    Assert.assertFalse(model.isDiscFlipped(5, 3));
    Assert.assertFalse(model.isDiscFlipped(5, 4));
    Assert.assertFalse(model.isDiscFlipped(6, 3));

    Assert.assertTrue(model.isDiscFlipped(2, 2));
    Assert.assertTrue(model.isDiscFlipped(2, 3));
    Assert.assertTrue(model.isDiscFlipped(2, 4));
    Assert.assertTrue(model.isDiscFlipped(2, 4));
    Assert.assertTrue(model.isDiscFlipped(2, 2));
    Assert.assertTrue(model.isDiscFlipped(4, 3));


    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(4, 0));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(4, 6));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(5, 0));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(5, 1));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(5, 5));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(5, 6));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 0));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 1));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 2));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 4));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 5));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(6, 6));

  }

  @Test
  public void testMoveDown() {
    model.startGame(7);
    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - - - - n n n
    // 2 - - o x - - n n
    // 3 - - x - o - - n
    // 4 - - o x - - n n
    // 5 - - - - - n n n
    // 6 - - - - n n n n
    // x is white o is black
    // starting state, in 2d array form, not in actual textual view form

    model.pass();

    // make a move downwards with white, gaining control of a new disc and capturing a black one
    model.makeMove(2, 5);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 5).getColor());

    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - - - - n n n
    // 2 - - o x - - n n
    // 3 - - x - o - - n
    // 4 - - x x - - n n
    // 5 - - x - - n n n
    // 6 - - - - n n n n
    // x is white o is black
   // result of move

    model.pass();

    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(2, 6));

    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - - - - n n n
    // 2 - - o x - - n n
    // 3 - - x - o - - n
    // 4 - - x x - - n n
    // 5 - - x - - n n n
    // 6 - - e - n n n n
    // x is white o is black
    // White is trying to move to the disc denoted as 'e', which is an invalid move
    // an exception is thrown

    // pass turn to black
    model.pass();

    // black makes a move downwards capturing 3 white discs and gaining control of a new disc
    model.makeMove(2, 6);
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 6).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 5).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 3).getColor());
    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - - - - n n n
    // 2 - - o x - - n n
    // 3 - - o - o - - n
    // 4 - - o x - - n n
    // 5 - - o - - n n n
    // 6 - - o - n n n n
    // x is white o is black
    // result of move
  }


  @Test
  public void moveUp() {
    model.startGame(7);

    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - - - - n n n
    // 2 - - o x - - n n
    // 3 - - x - o - - n
    // 4 - - o x - - n n
    // 5 - - - - - n n n
    // 6 - - - - n n n n
    // x is white o is black
    // move up
    // 2,1, 2,2 2,3 end  (1,2 2,2 3,2)
    // move down
    // 2,1 2,0 end (1,2 0,2)
    // move down means that we are picking a coordinate and
    model.pass();
    model.makeMove(2, 1);
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 1).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 2).getColor());

    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - x - - n n n
    // 2 - - x x - - n n
    // 3 - - x - o - - n
    // 4 - - o x - - n n
    // 5 - - - - - n n n
    // 6 - - - - n n n n

  }

  @Test
  public void testMoveRight() {
    // / 0 1 2 3 4 5 6 7
    // 0 - - - - n n n n
    // 1 - - x - - n n n
    // 2 - - x x - - n n
    // 3 - - x - o - - n
    // 4 - - o o o - n n
    // 5 - - - - - n n n
    // 6 - - - - n n n n
    model.startGame(7);
    model.makeMove(4,4);
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(4,4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3,4).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2,4).getColor());

  }

  @Test
  public void testMoveLeft() {

  }

  @Test
  public void testBoardImage() {
    ReversiHexModel rhm1 = new ReversiHexModel();
    rhm1.startGame(7);
    System.out.println("hello");

  }
}


























