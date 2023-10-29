import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import discs.DiscColor;
import model.Posn;
import model.ReversiHexModel;
import model.ReversiModel;
import player.PlayerTurn;

public class ReversiHexModelTests {
  ReversiModel model;
  Posn origin;
  PlayerTurn player1;
  PlayerTurn player2;

  @Before
  public void initData() {
    this.model = new ReversiHexModel();
    this.origin = new Posn(0, 0);
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
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(origin));
    Assert.assertThrows(IllegalStateException.class, () -> model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> model.currentTurn());
    Assert.assertThrows(IllegalStateException.class,
            () -> model.getPlayerScore(model.currentTurn()));
    Assert.assertThrows(IllegalStateException.class, () -> model.getDiscAt(origin));
    Assert.assertThrows(IllegalStateException.class, () -> model.isDiscFlipped(origin));
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

    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 0)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 1)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 2)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 3)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 4)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 5)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(0, 6)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 0)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 1)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 2)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 3)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 4)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 5)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(1, 6)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 0)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 1)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 5)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 6)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(3, 0)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(3, 1)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 5)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(2, 6)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(4, 1)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(4, 2)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(4, 4)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(4, 5)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(5, 2)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(5, 3)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(5, 4)));
    Assert.assertFalse(model.isDiscFlipped(new Posn(6, 3)));

    Assert.assertTrue(model.isDiscFlipped(new Posn(2, 2)));
    Assert.assertTrue(model.isDiscFlipped(new Posn(2, 3)));
    Assert.assertTrue(model.isDiscFlipped(new Posn(2, 4)));
    Assert.assertTrue(model.isDiscFlipped(new Posn(2, 4)));
    Assert.assertTrue(model.isDiscFlipped(new Posn(2, 2)));
    Assert.assertTrue(model.isDiscFlipped(new Posn(4, 3)));


    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(4, 0)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(4, 6)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(5, 0)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(5, 1)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(5, 5)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(5, 6)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 0)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 1)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 2)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 4)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 5)));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> model.isDiscFlipped(new Posn(6, 6)));

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

    // black is first to move
    // pass move to white
    model.pass();

    // make a move downwards with white, gaining control of a new disc and capturing a black one
    model.makeMove(new Posn(2, 5));
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(new Posn(2, 4)).getColor());
    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(new Posn(2, 5)).getColor());

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

    // pass blacks turn so that white can make a move
    model.pass();

    // try to capture your own disc as white
    Assert.assertThrows(IllegalStateException.class, () -> model.makeMove(new Posn (2, 6)));

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
    model.makeMove(new Posn(2, 6));
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(new Posn(2, 6)).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(new Posn(2, 5)).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(new Posn(2, 4)).getColor());
    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(new Posn(2, 3)).getColor());
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

//  @Test
//  public void moveUp() {
//    model.startGame(7);
//
//    // / 0 1 2 3 4 5 6 7
//    // 0 - - - - n n n n
//    // 1 - - - - - n n n
//    // 2 - - o x - - n n
//    // 3 - - x - o - - n
//    // 4 - - o x - - n n
//    // 5 - - - - - n n n
//    // 6 - - - - n n n n
//    // x is white o is black
//
//    model.pass();
//    model.makeMove(new Posn(2, 1));
//    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(new Posn(2, 1)).getColor());
//    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(new Posn(2, 2)).getColor());
//
//  }

}


























