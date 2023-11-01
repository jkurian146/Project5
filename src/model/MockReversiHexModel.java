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
public class MockReversiHexModel implements ReversiModel {
  private boolean gameOn;
  private Disc[][] gameBoard;
  private PlayerTurn pt;
  private  DiscType type;
  private  Map<PlayerTurn, DiscColor> playerColorMap;
  private int numRows;
  private int numColumns;
  private StringBuilder playerAction;
  private GameState state;

  /**
   * Constructor for a Reversi hexagonal model.
   * Sets the type of disc for this game as a hexagonal disc.
   */
  public MockReversiHexModel(Disc[][] mockBoard) {
    this.gameBoard = mockBoard;
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = DiscType.HEXDISC;
    this.pt = PlayerTurn.PLAYER1;
    this.playerAction = new StringBuilder();
    this.state = GameState.ONGOING;
    this.playerColorMap = new HashMap<>();
    playerColorMap.put(PlayerTurn.PLAYER1, DiscColor.BLACK);
    playerColorMap.put(PlayerTurn.PLAYER2, DiscColor.WHITE);
  }

  public MockReversiHexModel() {
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = DiscType.HEXDISC;
    this.gameBoard = null;
    this.pt = PlayerTurn.PLAYER1;
    this.playerAction = new StringBuilder();
    this.state = GameState.ONGOING;

    this.playerColorMap = new HashMap<>();
    playerColorMap.put(PlayerTurn.PLAYER1, DiscColor.BLACK);
    playerColorMap.put(PlayerTurn.PLAYER2, DiscColor.WHITE);
  }

  /**
   * Helps create initial game board by placing discs into the null 2D array gameBoard.
   *
   * @param spacesMaxLeft The maximum null spaces for this row to the left of the discs
   * @param spacesMaxRight The maximum null spaces for this row to the right of the discs
   * @param sb1
   * @param i
   */
  private void placeGameDiscs(int spacesMaxLeft, int spacesMaxRight, StringBuilder sb1, int i) {
    sb1.append("\n");
    for (int j = 0; j < this.gameBoard[0].length; j++) {
      if (j >= spacesMaxLeft && j < this.gameBoard.length - spacesMaxRight) {
        sb1.append("-");
        this.gameBoard[i][j] = new GameDisc(this.type, DiscColor.FACEDOWN);
      } else {
        sb1.append("n");
      }
    }
  }

  /**
   * Helps create the initial game board when the middle row is odd.
   */


  private void setPiece(int x, int y, DiscColor color) {
    GameDisc replacementDisc = new GameDisc(this.type, color);

    this.gameBoard[y][x] = replacementDisc;
  }


  @Override
  public void startGame(int boardSize) {
    this.gameOn = true;
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
    res.add(bfsHelper(destX,destY,MoveDirection.LEFT,new ArrayList<>(), true));
    res.add(bfsHelper(destX,destY,MoveDirection.RIGHT, new ArrayList<>(), true));
    res.add(bfsHelper(destX,destY,MoveDirection.UPLEFT, new ArrayList<>(), true));
    res.add(bfsHelper(destX,destY,MoveDirection.UPRIGHT, new ArrayList<>(), true));
    res.add(bfsHelper(destX,destY,MoveDirection.DOWNLEFT, new ArrayList<>(), true));
    res.add(bfsHelper(destX,destY,MoveDirection.DOWNRIGHT, new ArrayList<>(), true));
    return res;
  }
  private List<List<Integer>> bfsHelper(int x, int y, MoveDirection moveDirection, List<List<Integer>> res,
                                        boolean firstPass) {

    while (true) {
      res.add(Arrays.asList(x,y));
      DiscColor playerTurnColor = this.getPlayerColor(this.pt);
      List<Integer> nextPos = MoveRules.applyShiftBasedOnDirection(x,y,moveDirection);
      int nextPosX = nextPos.get(0);
      int nextPosY = nextPos.get(1);
      x = nextPosX;
      y = nextPosY;
      if (!this.checkValidCoordinates(nextPosX, nextPosY)) {
        return new ArrayList<>();
      }
      if (firstPass) {
        DiscColor opponentTurnColor = this.getPlayerColor(getOpponent(this.pt));
        if (opponentTurnColor == this.getDiscAt(nextPosX, nextPosY).getColor()) {
          res.add(Arrays.asList(x, y));
          res.add(Arrays.asList(nextPosX, nextPosY));
        } else {
          return new ArrayList<>();
        }
        firstPass = false;
      } else {
        if (this.getDiscAt(nextPosX,nextPosY).getColor() == playerTurnColor) {
          return res;
        } else if (this.getDiscAt(nextPosX,nextPosY).getColor() == DiscColor.FACEDOWN) {
          return new ArrayList<>();
        } else {
          res.add(Arrays.asList(nextPosX,nextPosY));
        }
      }
    }
  }
  @Override
  public void makeMove(int x, int y) {
    // Check if the game has not yet started
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
    this.playerAction.append(this.getPlayerColor(this.pt).toString())
            .append(" moved to (").append(x).append(y).append("), ");
  }


  // setDisc(x,y,color)

  private void applyColorFilter(List<Integer> list, DiscColor discColor) {
    this.setPiece(list.get(0),list.get(1), discColor);
  }

  private boolean consecutivePasses() {
    List<String> moveHistory = Arrays.asList(this.playerAction.toString().split(" "));

    if(moveHistory.size() >= 2) {
      String last = moveHistory.get(moveHistory.size() -1);
      String penultimate = moveHistory.get(moveHistory.size() -2);

      return last.equals("pass") && penultimate.equals("pass");
    } else {
      return false;
    }
  }


  @Override
  public Boolean isGameOver() {
    boolean noMoves = this.noMoreLegalMoves();
    boolean twoPassesInARow = this.consecutivePasses();
    boolean currentPlayerLost = this.getPlayerScore(this.pt) == 0;
    boolean oppositePlayerLost = this.getPlayerScore(this.getOpponent(this.pt)) == 0;

    if (twoPassesInARow) {
      this.state = GameState.STALEMATE;
      return true;
    }
    if (currentPlayerLost) {
      this.state = (this.pt == PlayerTurn.PLAYER1) ? GameState.PLAYER2WIN: GameState.PLAYER1WIN;
      return true;
    }
    if (oppositePlayerLost) {
      this.state = (this.pt == PlayerTurn.PLAYER1) ? GameState.PLAYER1WIN : GameState.PLAYER2WIN;
      return true;
    }
    if (noMoves) {
      this.state = GameState.STALEMATE;
      return true;
    }
    return false;
  }

  private Boolean noMoreLegalMoves() {
    for(int i = 0; i < this.gameBoard.length; i ++) {
      for(int j = 0; j < this.gameBoard[0].length; j ++) {
        if(this.gameBoard[j][i] == null) {
          int doNothing = 0;
        }
        else if(this.gameBoard[j][i].getColor() == DiscColor.FACEDOWN) {
          List<List<List<Integer>>> moves = bfs(i,j);

          boolean allEmptyLists = moves.stream().allMatch(List::isEmpty);
          if(!allEmptyLists) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public PlayerTurn currentTurn() {

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

  private PlayerTurn getOpponent(PlayerTurn player) {
    if(PlayerTurn.PLAYER2 == player) {
      return PlayerTurn.PLAYER1;
    }
    return PlayerTurn.PLAYER2;
  }

  private int getPlayerScore(PlayerTurn player) {

    int countDiscsForThisPlayer = 0;

    for (Disc[] row : this.gameBoard) {
      for (Disc disc : row) {
        if(disc == null) {
          countDiscsForThisPlayer += 0;
        } else if (disc.getColor() == this.getPlayerColor(player)) {
          countDiscsForThisPlayer++;
        }
      }
    }
    return countDiscsForThisPlayer;
  }

  @Override
  public Disc getDiscAt(int x, int y) {
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[y][x];
  }

  @Override
  public boolean isDiscFlipped(int x, int y) {
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("POSN provided by user is invalid");
    }
    return this.gameBoard[y][x].getColor() != DiscColor.FACEDOWN;
  }

  @Override
  public void pass() {
    this.togglePlayer();

    this.playerAction.append("pass ");
  }

  @Override
  public int getDimensions() {
    return 0;
  }

  @Override
  public Disc[][] getBoard() {
    return new Disc[0][];
  }

  private void togglePlayer() {
    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}