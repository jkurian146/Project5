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
  }
  @Override
  public void makeMove(int r, int q) {

  }

  @Override
  public PlayerTurn isGameOver() {
    return this.pt;
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
  public int getScore() {
    return 0;
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
