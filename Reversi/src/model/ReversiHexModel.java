package model;

import java.util.HashMap;
import java.util.Map;

import discs.DiscType;
import player.PlayerTurn;
import discs.Disc;
import discs.DiscType;
import discs.DiscColor;
import discs.GameDisc;

/**
 * A 'ReversiHexModel' defines a hexagonal Reversi game.
 */
public class ReversiHexModel implements ReversiModel {
  private boolean gameOn;
  private Disc[][] gameBoard;
  private PlayerTurn pt;
  private final DiscType type;
  private final Map<PlayerTurn, DiscColor> playerColorMap;
  private int numRows;
  private int numColumns;

  /**
   * Constructor for a Reversi hexagonal model.
   * Sets the type of disc for this game as a hexagonal disc.
   */
  public ReversiHexModel() {
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = DiscType.HEXDISC;
    this.gameBoard = null;
    this.pt = PlayerTurn.PLAYER1;

    this.playerColorMap = new HashMap<>();
    playerColorMap.put(PlayerTurn.PLAYER1, DiscColor.BLACK);
    playerColorMap.put(PlayerTurn.PLAYER2, DiscColor.WHITE);
  }

  /**
   * Creates the initial gameBoard of a hexagonal reversi with the appropriate features.
   */
  private void initBoard() {
    StringBuilder sb1 = new StringBuilder();
    int middle = this.gameBoard[0].length / 2;
    for (int i = 0; i < this.gameBoard.length; i++) {
      int spotsOpen = Math.abs(middle - i);
      sb1.append("\n");
      for (int j = 0; j < this.gameBoard[i].length; j++) {
        if (j < this.gameBoard.length - spotsOpen) {
          this.gameBoard[i][j] = new GameDisc(DiscType.HEXDISC, DiscColor.FACEDOWN);
          sb1.append("*");
        } else {
          sb1.append(" ");
        }
      }
    }
    System.out.println(sb1.toString());
  }

  /**
   * Checks that the given inputs to start a hexagonal reversi are valid. Throws an Exception if
   * given inputs result in an invalid start-game state, otherwise does nothing.
   *
   * @param numRows    Number of rows to start the game with
   * @param numColumns Number of columns to start the game with
   */
  private void checkStartGameConditions(int numRows, int numColumns) {
    if (this.gameOn) {
      throw new IllegalStateException("Game has already started");
    }
    if ((numRows == 3 && numColumns == 3)
            || numRows < 3
            || numColumns < 3
            || numRows % 2 == 0
            || numColumns % 2 == 0) {
      throw new IllegalArgumentException("Board is too small.");
    }
  }

  @Override
  public void startGame(int numRows, int numColumns) {
    checkStartGameConditions(numRows, numColumns);

    this.gameOn = true;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.gameBoard = new Disc[numRows][numColumns];
    initBoard();
  }

  /**
   * Helper method for all in-game methods that throws an IllegalStateException
   * if the game has not yet been started.
   *
   * @throws IllegalArgumentException if the game is inactive
   */
  private void gameNotYetStarted() {
    if (!this.gameOn) {
      throw new IllegalStateException("The game hasn't started.");
    }
  }

  /**
   * Helper method to check if the given coordinate inputs are valid.
   * Does nothing if coordinates are valid, throws an exception if invalid.
   *
   * @param row    the row of the desired disc
   * @param column the column of the desired disc
   * @throws IllegalArgumentException if given coordinates are negative, out of bounds,
   *                                  or if the desired disc is null
   */
  private void checkValidCoordinates(int row, int column) {
    if (row < 0 || column < 0 || row >= this.gameBoard.length
            || this.gameBoard[row][column] == null) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
  }

  @Override
  public void makeMove(int row, int column) {
    this.gameNotYetStarted();
    this.checkValidCoordinates(row, column);

  }

  @Override
  public Boolean isGameOver() {
    this.gameNotYetStarted();

    return false;
  }

  @Override
  public PlayerTurn currentTurn() {
    this.gameNotYetStarted();

    return this.pt;
  }

  /**
   * Returns the color associated with the given player.
   *
   * @param player the player to get the color of
   * @return the DiscColor of the given player
   */
  private DiscColor getPlayerColor(PlayerTurn player) {
    return playerColorMap.get(player);
  }

  @Override
  public int getPlayerScore(PlayerTurn player) {
    this.gameNotYetStarted();

    int countDiscsForThisPlayer = 0;

    for (Disc[] row : this.gameBoard) {
      for (Disc disc : row) {
        if (disc.getColor() == this.getPlayerColor(player)) {
          countDiscsForThisPlayer++;
        }
      }
    }
    return countDiscsForThisPlayer;
  }

  @Override
  public Disc getDiscAt(int row, int column) {
    this.gameNotYetStarted();
    this.checkValidCoordinates(row, column);

    return this.gameBoard[row][column];
  }

  @Override
  public boolean isDiscFlipped(int row, int column) {
    this.gameNotYetStarted();
    this.checkValidCoordinates(row, column);

    return this.gameBoard[row][column].getColor() != DiscColor.FACEDOWN;
  }

  @Override
  public void toggleEnum() {
    this.gameNotYetStarted();

    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}
