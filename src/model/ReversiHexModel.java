package model;

import player.PlayerTurn;
import tiles.ITile;
import tiles.Tile;
import tiles.TileColor;
import tiles.TileImpl;

/**
 * A 'ReversiHexModel' defines a hexagonal Reversi game.
 */
public class ReversiHexModel implements ReversiModel {
  private boolean gameOn;
  private ITile[][] gameBoard;
  private PlayerTurn pt;
  private final Tile type;
  private int numRows;
  private int numColumns;


  public ReversiHexModel() {
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = Tile.HEXTILE;
    this.gameBoard = null;
    this.pt = PlayerTurn.PLAYER1;
  }

  @Override
  public void startGame(int numRows, int numColumns) {
    checkStartGameConditions(numRows, numColumns);
    this.gameOn = true;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.gameBoard = new ITile[numRows][numColumns];
    initBoard();
  }

  private void checkStartGameConditions(int numRows, int numColumns) {
    if (this.gameOn) {
      throw new IllegalStateException("Game has already started");
    }
    if ((numRows == 3 && numColumns == 3)
            || numRows < 3
            || numColumns < 3
            || numRows % 2 == 0
            || numColumns % 2 == 0) {
      throw new IllegalArgumentException("Board is too small");
    }
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
          this.gameBoard[i][j] = new TileImpl(Tile.HEXTILE, TileColor.FLIPPED);
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
  public PlayerTurn currentTurn() {
    return this.pt;
  }

  @Override
  public int getPlayerScore(PlayerTurn player) {
    return 0;
  }

  // return null if empty spot on board throw catchn for out of bounds throw illegal arg
  @Override
  public ITile getTileAt(int r, int q) {
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
