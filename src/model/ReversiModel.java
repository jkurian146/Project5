package model;

import player.PlayerTurn;
import tiles.Tile;

/**
 * The `ReversiGame` interface defines the model in a Reversi game.
 * The model is responsible for managing the game state, enforcing game rules, and providing
 * game-related information to other components.

 * Reversi is a two-player board game played on a hexagonal grid where each player
 * aims to have their discs outnumber their opponent's discs by the end of the game.
 * <p>
 * Players take turns placing their disc on the board
 * A valid move requires that a player's disc be placed adjacent to a line of
 * the opponent's discs, with another disc of the player's color at the opposite end.
 * The opponent's discs within this line are then "captured" and flipped to the player's color.
 * If a player has no valid moves, they must pass their turn.
 * The game ends when both players pass consecutively or the board is full.
 * The player with the most discs on the board at the end wins.
 * <p>
 * The game board is a regular grid of hexagonal cells. Initially, an equal number of white and
 * black discs are placed in the center of the grid.
 * Players then add discs to the board during their turn.
 * <p>
 * There are two players, one for each disc color (black and white).
 * Players can be human or computer-controlled.
 * <p>
 * A disc may be placed in any empty cell adjacent to an opponent's disc.
 * If placing a disc forms a line (in any direction) between one of the player's existing discs
 * and the newly placed one, with only the opponent's discs in between,
 * those opponent discs are captured.
 * A player may pass if they wish, but must pass their turn if they have no valid moves available.
 */

public interface ReversiModel {

  /**
   * Starts a new game of Reversi.
   *
   * @param numRows    the number of rows to play with
   * @param numColumns the number of columns to play with
   * @param type       the type of tile
   * @throws IllegalArgumentException If the given number of rows or columns are even,
   *                                  or severely disproportionate to each other
   * @throws IllegalStateException    if the game has already started
   */
  void startGame(int numRows, int numColumns, Tile type);

  /**
   * Makes a move on the Reversi board by placing a tile on the specified tile.
   * This method will place the current player's tile on the given row and column.
   * If the move is successful, it will flip any of the opponent's discs that are surrounded
   * in straight lines (horizontally, vertically, or diagonally) between the newly placed tile
   * and another tile of the current player.
   *
   * @param row    row of the desired tile (0-indexed from the top)
   * @param column column of the desired tile (0-indexed from the left)
   * @throws IllegalArgumentException If the attempted move is invalid
   * @throws IllegalStateException    if the game hasn't been started yet
   */
  void makeMove(int row, int column);

  /**
   * Signal if the game is over or not. A game is over if one of the players does not
   * occupy any tiles, or if both players have no legal moves available,
   * or if all tiles on the board are occupied.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  Boolean isGameOver();

  /**
   * Returns the player who made the latest move.
   *
   * @return the last player to make a move
   * @throws IllegalStateException if the game hasn't been started yet
   */
  PlayerTurn latestTurn();

  /**
   * Returns the score of the given player.
   *
   * @param player the player to evaluate
   * @return the number of tiles that the given player occupies
   * @throws IllegalStateException if the game hasn't been started yet
   */
  int getPlayerScore(PlayerTurn player);

  /**
   * Returns the tile at the specified coordinates.
   *
   * @param row    row of the desired tile (0-indexed from the top)
   * @param column column of the desired tile (0-indexed from the left)
   * @return the desired tile
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  Tile getTileAt(int row, int column);

  /**
   * Returns whether the tile at the specified coordinates is flipped or not.
   *
   * @param row    row of the desired tile (0-indexed from the top)
   * @param column column of the desired tile (0-indexed from the left)
   * @return whether the tile at the given position is flipped or not
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  boolean isTileFlipped(int row, int column);

  /**
   * Toggles between player turns
   *
   * @throws IllegalStateException if the game hasn't been started yet
   */
  public void toggleEnum();

}