package model;

import player.PlayerTurn;
import tiles.Tile;

/**
 * The `ReversiGame` interface defines the model in a Reversi game.
 * The model is responsible for managing the game state, enforcing game rules, and providing
 * game-related information to other components.
 */
public interface ReversiModel {

  void startGame(int numRows, int numColumns, Tile type);

  void makeMove(int r, int q);

  PlayerTurn isGameOver();

  Tile getTileAt(int r, int q);

  boolean isTileFlipped(int r, int q);

  int getScore();
  public void toggleEnum();

}
