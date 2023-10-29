package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
   * @param boardSize the square dimension nxn to create the game board with
   */
  private void checkStartGameConditions(int boardSize) {
    if (this.gameOn) {
      throw new IllegalStateException("Game has already started");
    } else if (boardSize <= 3 || boardSize % 2 == 0) {
      throw new IllegalArgumentException("Invalid Board Sizes");
    }
  }

  @Override
  public void startGame(int boardSize) {
    this.numRows = boardSize;
    this.numColumns = boardSize;
    checkStartGameConditions(boardSize);

    this.gameOn = true;
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
   * @param posn the coordinate to be evaluated
   * @throws IllegalArgumentException if given coordinates are negative, out of bounds,
   *                                  or if the desired disc is null
   */
  private boolean checkValidCoordinates(Posn posn) {
    int x = posn.getX();
    int y = posn.getY();
    if (x >= this.gameBoard.length || y >= this.gameBoard.length || x < 0 || y < 0) {
      return false;
    }
    return this.gameBoard[x][y] != null;
  }

  private List<Posn> moveDown(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX(), posn.getY() + 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveUp(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX(), posn.getY() - 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveRight(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() + 1, posn.getY());

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveLeft(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() - 1, posn.getY());

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveLeftDown(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() - 1, posn.getY() + 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveRightDown(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() + 1, posn.getY() + 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveRightUp(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() + 1, posn.getY() - 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  private List<Posn> moveLeftUp(Posn posn) {
    List<Posn> list = new ArrayList<>();
    PlayerTurn current = this.currentTurn();

    boolean moveNotFound = true;

    DiscColor color = this.getPlayerColor(current);

    while (checkValidCoordinates(posn) && moveNotFound) {
      posn.set(posn.getX() - 1, posn.getY() - 1);

      if (this.getDiscAt(posn).getColor() == color) {
        moveNotFound = false;
      } else {
        list.add(posn);
      }
    }
    return list;
  }

  @Override
  public void makeMove(Posn posn) {
    this.gameNotYetStarted();

    List<List<Posn>> list = new ArrayList<>();

    if (this.getDiscAt(posn).getColor() != DiscColor.FACEDOWN) {
      throw new IllegalStateException("Invalid Move");
    }
    list.add(this.moveDown(posn));
    list.add(this.moveUp(posn));
    list.add(this.moveRight(posn));
    list.add(this.moveLeft(posn));
    list.add(this.moveRightDown(posn));
    list.add(this.moveLeftDown(posn));
    list.add(this.moveRightUp(posn));
    list.add(this.moveLeftUp(posn));

    for (int i = 0; i < list.size(); i++) {
      List<Posn> currentList = list.get(i);
      applyColorFilter(currentList, this.getPlayerColor(this.pt));
    }
  }
  // setDisc(x,y,color)
  private void applyColorFilter(List<Posn> list, DiscColor discColor) {
    // loop through posn's and use setDisc to the new disc color
    for (int i = 0; i < list.size(); i++) {
      // posn currentPosn = list.get(i)
      // setDisc(currentPosn.getX(), list.get(i).get(y), discColor)
    }
  }

  @Override
  public Boolean isGameOver() {
    this.gameNotYetStarted();

    List<List<Posn>> list = new ArrayList<>();

    for (int i = 0; i < this.gameBoard.length; i++) {
      for (int j = 0; j < this.gameBoard[0].length; j++) {
        Posn posn = new Posn(i, j);
        DiscColor color = this.getDiscAt(posn).getColor();
        if (color == DiscColor.FACEDOWN) {
          list.add(this.moveDown(posn));
          list.add(this.moveUp(posn));
          list.add(this.moveRight(posn));
          list.add(this.moveLeft(posn));
          list.add(this.moveRightDown(posn));
          list.add(this.moveLeftDown(posn));
          list.add(this.moveRightUp(posn));
          list.add(this.moveLeftUp(posn));
        }
      }
    }
    if (list.isEmpty()) {
      this.gameOn = false;
      return true;
    } else {
      return false;
    }
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
  public Disc getDiscAt(Posn posn) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(posn)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[posn.getX()][posn.getY()];
  }

  @Override
  public boolean isDiscFlipped(Posn posn) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(posn)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[posn.getX()][posn.getY()].getColor() != DiscColor.FACEDOWN;
  }

  @Override
  public void pass() {
    this.gameNotYetStarted();
    this.togglePlayer();
  }

  @Override
  public void togglePlayer() {
    this.gameNotYetStarted();
    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}
