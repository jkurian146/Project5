package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import discs.DiscType;
import player.PlayerTurn;
import discs.Disc;
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
    this.setStartingPieces();
    System.out.println(sb1.toString());
  }

  private void setPiece(int x, int y, DiscColor color) {
    GameDisc replacementDisc = new GameDisc(this.type, color);

    this.gameBoard[y][x] = replacementDisc;
  }

  private void setStartingPieces() {


    this.setPiece(2, 2, DiscColor.BLACK);
    this.setPiece(2, 4, DiscColor.BLACK);
    this.setPiece(4, 3, DiscColor.BLACK);
    this.setPiece(3, 2, DiscColor.WHITE);
    this.setPiece(2, 3, DiscColor.WHITE);
    this.setPiece(3, 4, DiscColor.WHITE);

    // / 0 1 2 3 4 5 6
    // 0 - - - - n n n
    // 1 - - - - - n n
    // 2 - - o x - - n
    // 3 - - x - o - -
    // 4 - - o x - - n
    // 5 - - - - - n n
    // 6 - - - - n n n
  }

  public Disc[][] getBoard() {
    return this.gameBoard;
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
   * @param x,y the coordinate to be evaluated
   * @throws IllegalArgumentException if given coordinates are negative, out of bounds,
   *                                  or if the desired disc is null
   */
  private boolean checkValidCoordinates(int x, int y) {

    if (x >= this.gameBoard.length || y >= this.gameBoard.length || x < 0 || y < 0) {
      return false;
    }
    return this.gameBoard[y][x] != null;
  }

  private List<List<Integer>> moveDown(int x, int y) {
    List<Integer> firstPos = new ArrayList<>(Arrays.asList(x,y));
    List<List<Integer>> list = new ArrayList<>();
    list.add(firstPos);
    PlayerTurn current = this.currentTurn();
    DiscColor color = this.getPlayerColor(current);

    while (true) {
      y--;
      if (!this.checkValidCoordinates(x,y) || this.getDiscAt(x,y).getColor() != color) {
        break;
      } else {
        List<Integer> tempList = new ArrayList<>(Arrays.asList(x,y));
        list.add(tempList);
      }
    }
    if(list.size() == 1) {
      list.remove(0);
    }
    return list;
  }

  private List<List<Integer>> moveUp(int x, int y) {
    List<Integer> firstPos = new ArrayList<>(Arrays.asList(x,y));
    List<List<Integer>> list = new ArrayList<>();
    list.add(firstPos);
    PlayerTurn current = this.currentTurn();
    DiscColor color = this.getPlayerColor(current);

    while (true) {
      y++;
      if (!this.checkValidCoordinates(x,y) || this.getDiscAt(x,y).getColor() != color) {
        break;
      } else {
        List<Integer> tempList = new ArrayList<>(Arrays.asList(x,y));
        list.add(tempList);
        System.out.println("X: " + x + " Y: " + y);
      }
    }
    if(list.size() == 1) {
      list.remove(0);
    }
    return list;
  }

  @Override
  public void makeMove(int x, int y) {
    // Check if the game has not yet started
    this.gameNotYetStarted();
    List<Integer> originalCoordinate = new ArrayList<>();
    originalCoordinate.add(x);
    originalCoordinate.add(y);

    // Check if the provided coordinates are valid
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("Invalid coordinates provided by the user.");
    }

    // Check if the specified position contains a facedown disc
    if (this.getDiscAt(x, y).getColor() != DiscColor.FACEDOWN) {
      throw new IllegalStateException("Invalid Move: Disc is not facedown.");
    }

    // Initialize a list for possible moves
    List<List<List<Integer>>> moves = new ArrayList<>();

    // Add the results of your move methods (e.g., moveDown, moveUp, etc.) to the list
    moves.add(this.moveDown(originalCoordinate.get(0), originalCoordinate.get(1)));
    moves.add(this.moveUp(originalCoordinate.get(0), originalCoordinate.get(1)));
    // Add other move methods as needed

    // Check if all move lists are empty
    boolean allEmptyLists = moves.stream().allMatch(List::isEmpty);

    if (allEmptyLists) {
      throw new IllegalStateException("Invalid move: No valid moves found.");
    }

    // Apply color filter to each inner list and toggle the player
    for (List<List<Integer>> l : moves ) {
      l.forEach(innerList -> applyColorFilter(innerList, this.getPlayerColor(this.pt)));
    }
    this.togglePlayer();
  }


  // setDisc(x,y,color)

  private void applyColorFilter(List<Integer> list, DiscColor discColor) {
    this.setPiece(list.get(0),list.get(1), discColor);
  }

  @Override
  public Boolean isGameOver() {
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
  public Disc getDiscAt(int x, int y) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[y][x];
  }

  @Override
  public boolean isDiscFlipped(int x, int y) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[y][x].getColor() != DiscColor.FACEDOWN;
  }

  @Override
  public void pass() {
    this.gameNotYetStarted();
    this.togglePlayer();
  }

  private void togglePlayer() {
    this.gameNotYetStarted();
    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}