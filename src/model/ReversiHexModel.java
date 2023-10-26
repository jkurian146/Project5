package model;

import player.PlayerTurn;
import tiles.Tile;

/**
 * A 'ReversiHexModel' defines a hexagonal Reversi game.
 */
public class ReversiHexModel implements ReversiModel {
  private boolean gameOn;
  private Tile[][] gameBoard;
  private  PlayerTurn pt;
  private Tile type;
  private int numRows;
  private int numColumns;

  public ReversiHexModel() {
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = null;
    this.gameBoard = null;
    this.pt = PlayerTurn.PLAYER1;

  }

  @Override
  public void startGame(int numRows, int numColumns, Tile type) {
    this.gameOn = true;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.type = type;
    this.gameBoard = new Tile[numRows][numColumns];
    initBoard();
    System.out.println("hello");
  }
  // 11 x 7
  // nnn______nn   6
  // nn________n  8
  // n_________n  10
  // ___________ 11
  // n_________n  10
  // nn________n 8
  // nnn______nn 6

  //
  // nn_nn
  // n___n
  // _____
  // n___n
  // nn_nn

  // nn___nn 3
  // n_____n 5
  // _______ 7
  // n_____n 5
  // nn___nn
  private void initBoard() {
    StringBuilder sb1 = new StringBuilder();
    int middle = this.gameBoard[0].length / 2;
    for (int i = 0; i < this.gameBoard.length; i++) {
      int spotsOpen = Math.abs(middle - i);
      sb1.append("\n");
      for (int j = 0; j < this.gameBoard[i].length; j++) {
        if (j < this.gameBoard.length - spotsOpen) {
          this.gameBoard[i][j] = Tile.HEXTILE;
          sb1.append("*");
        }else {
          sb1.append(" ");
        }
      }
      }
    System.out.println(sb1.toString());
    }

  @Override
  public void makeMove(int r, int q) {

  }

  @Override
  public Boolean isGameOver() {
    return false;
  }

  @Override
  public PlayerTurn latestTurn() {
    return null;
  }

  @Override
  public int getPlayerScore(PlayerTurn player) {
    return 0;
  }

  @Override
  public Tile getTileAt(int r, int q) {
    return this.gameBoard[r][q];
  }

  @Override
  public boolean isTileFlipped(int r, int q) {
    return false;
  }



  @Override
  public void toggleEnum() {
    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}
