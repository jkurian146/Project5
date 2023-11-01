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
    int middle = this.gameBoard.length / 2;


    double MaxSpaces = (double) this.gameBoard.length - ((double) (this.gameBoard.length + 1) / 2);
    int SpacesMaxLeft = (int) Math.ceil(MaxSpaces / 2);
    int SpacesMaxRight = (int) (MaxSpaces - SpacesMaxLeft);
    boolean middleCrossed = false;
    StringBuilder sb1 = new StringBuilder();

    for (int i = 0; i < this.gameBoard.length; i++) {
      int distanceFromMiddle = Math.abs(middle - i);

      if (!middleCrossed) {
        if (distanceFromMiddle % 2 == 0) {
          SpacesMaxLeft -= 1;
        }
      } else {
        if (distanceFromMiddle % 2 != 0) {
          SpacesMaxLeft += 1;
        }
      }

      sb1.append("\n");
      for (int j = 0; j < this.gameBoard[0].length; j++) {
        if (j >= SpacesMaxLeft && j < this.gameBoard.length - SpacesMaxRight) {
          sb1.append("-");
          this.gameBoard[i][j] = new GameDisc(this.type, DiscColor.FACEDOWN);
        } else {
          sb1.append("n");
        }
      }

      if (!middleCrossed) {
        if (distanceFromMiddle % 2 == 0) {
          SpacesMaxRight -= 1;
        }
      } else {
        if (SpacesMaxRight < 0) {
          SpacesMaxRight = 0;
        }
        if (distanceFromMiddle % 2 != 0) {
          SpacesMaxRight += 1;
        }
      }

      if (i == middle) {
        middleCrossed = true;
      }
    }
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

  private List<List<List<Integer>>> bfs(int destX, int destY) {
    List<List<List<Integer>>> res = new ArrayList<>();
    ArrayList<MoveDirection> movesAvailable = new ArrayList<>(Arrays.asList(MoveDirection.values()));
    for (int i = 0; i < movesAvailable.size(); i++) {
      MoveDirection currentMoveDirection = movesAvailable.get(i);
      switch (currentMoveDirection) {
        case LEFT:
          res.add(bfsHelper(destX,destY,MoveDirection.LEFT,new ArrayList<>()));
        case RIGHT:
          res.add(bfsHelper(destX,destY,MoveDirection.RIGHT, new ArrayList<>()));
        case UPLEFT:
          res.add(bfsHelper(destX,destY,MoveDirection.UPLEFT, new ArrayList<>()));
        case UPRIGHT:
          res.add(bfsHelper(destX,destY,MoveDirection.UPRIGHT, new ArrayList<>()));
        case DOWNLEFT:
          res.add(bfsHelper(destX,destY,MoveDirection.DOWNLEFT, new ArrayList<>()));
        case DOWNRIGHT:
          res.add(bfsHelper(destX,destY,MoveDirection.DOWNRIGHT, new ArrayList<>()));
      }
    }
    return res;
  }
  private List<List<Integer>> bfsHelper(int x, int y, MoveDirection moveDirection, List<List<Integer>> res) {
    // will only return for one direction. this makes the res 2d not 3d
    DiscColor currentColor = this.getPlayerColor(this.pt);
    List<Integer> nextPos = MoveRules.applyShiftBasedOnDirection(x,y,moveDirection);
    int nextPosX = nextPos.get(0);
    int nextPosY = nextPos.get(1);
    if (!this.checkValidCoordinates(nextPosX,nextPosY)) {
      return new ArrayList<>();
    }
    if (this.getDiscAt(nextPosX,nextPosY).getColor() == DiscColor.FACEDOWN) {
      return new ArrayList<>();
    }
    if (this.getDiscAt(nextPosX,nextPosY).getColor() == currentColor) {
      res.add(Arrays.asList(nextPos.get(0), nextPos.get(1)));
      return res;
    }
    res.add(Arrays.asList(nextPos.get(0), nextPos.get(1)));
    bfsHelper(x, y, moveDirection, res);
    return null;
  }
  @Override
  public void makeMove(int x, int y) {
    // Check if the game has not yet started
    this.gameNotYetStarted();
    List<Integer> originalCoordinate = new ArrayList<>();
    originalCoordinate.add(x);
    originalCoordinate.add(y);
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("Invalid coordinates provided by the user.");
    }
    if (this.getDiscAt(x, y).getColor() != DiscColor.FACEDOWN) {
      throw new IllegalStateException("Invalid Move: Disc is not facedown.");
    }

    List<List<List<Integer>>> moves = bfs(x,y);
    boolean allEmptyLists = moves.stream().allMatch(List::isEmpty);

    if (allEmptyLists) {
      throw new IllegalStateException("Invalid move: No valid moves found.");
    }

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