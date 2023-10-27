package controller;

import model.ReversiModel;
import discs.ReversiDisc;

/**
 * The `ReversiController` interface defines the controller component in a Reversi game.
 * It acts as an intermediary between the players, the game model, and the view.
 * The controller is responsible for facilitating game flow and rules, but not enforcing them.
 */
public interface ReversiController {

  void playGame(ReversiModel model, int numRows, int numColumns);
}