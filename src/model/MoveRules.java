package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class MoveRules {
  public static List<Integer> applyShiftBasedOnDirection(int x, int y, MoveDirection moveDirection) {
    ArrayList<Integer> res = new ArrayList<>();
    switch (moveDirection) {
      case LEFT:
        res = applyShift(x,y,-1,0);
      case RIGHT:
        res = applyShift(x,y,1,0);
      case UPRIGHT:
        res = (y % 2 == 0) ? applyShift(x,y,0,-1) : applyShift(x,y,1,-1);
      case DOWNRIGHT:
        res = (y % 2 == 0) ? applyShift(x,y,0,1) : applyShift(x,y,1,1);
      case UPLEFT:
        res = (y % 2 == 0) ? applyShift(x,y,-1,-1) : applyShift(x,y,0,-1);
      case DOWNLEFT:
        res = (y % 2 == 0) ? applyShift(x,y,-1,1) : applyShift(x,y,0,1);
    }
    return res;
  }

  private static ArrayList<Integer> applyShift(int x, int y, int xShift, int yShift) {
    return new ArrayList<>(Arrays.asList(x+xShift,y+yShift));
  }
}
