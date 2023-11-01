package view;

import java.io.IOException;
import java.util.List;

import discs.Disc;
import discs.DiscColor;
import discs.GameDisc;
import model.ReversiModel;

public class TextualView implements ReversiTextualView {

  private final ReversiModel reversiModel;
  private Appendable appendable;

  public TextualView(ReversiModel reversiModel, Appendable appendable) {
    this.reversiModel = reversiModel;
    this.appendable = appendable;
  }
  @Override
  public String toString() {
    updateBoard();
    return this.appendable.toString();
  }

  public void updateBoard() {
    if (appendable.toString() != "") {
      this.appendable = new StringBuilder();
    }
    for (int i = 0; i < this.reversiModel.getDimensions(); i++) {
      if (i != 0) {
        this.addToStringBuilder("\n");
      }
      for (int j = 0; j < this.reversiModel.getDimensions(); j++) {
        try {
          Disc currentDisc = this.reversiModel.getDiscAt(j, i);
          if (currentDisc.getColor() == DiscColor.BLACK) {
            this.addToStringBuilder("X ");
          } else if (currentDisc.getColor() == DiscColor.WHITE) {
            this.addToStringBuilder("O ");
          } else if (currentDisc.getColor() == DiscColor.FACEDOWN) {
            this.addToStringBuilder("- ");
          }
        } catch (IllegalArgumentException iae) {

        }
      }
    }
    String boardWithoutExtraSpace = trimOriginalSpaces(this.appendable);
    this.appendable = new StringBuilder(boardWithoutExtraSpace);
    String boardAdjusted = addNSpacesToFrontAndBack(this.appendable);
    this.appendable = new StringBuilder(boardAdjusted);
  }

  private String addNSpacesToFrontAndBack(Appendable nonAdjusted) {
    StringBuilder res = new StringBuilder();
    List<String> lines = List.of(this.appendable.toString().split("\n"));
    for (int i = 0; i < lines.size(); i++) {
      int spacesToAdd = Math.abs((this.reversiModel.getDimensions() / 2) - i);
      String spaces = createNSpaces(spacesToAdd);
      String stringWithSpacesBeginning = spaces.concat(lines.get(i)).replace("\n","");
      String stringWithSpacesAtEnd = stringWithSpacesBeginning.concat(spaces);
      stringWithSpacesAtEnd += "\n";
      res.append(stringWithSpacesAtEnd);
    }
    return res.toString();
  }

  private String createNSpaces(int spacesToAdd) {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < spacesToAdd; i++) {
      res.append(" ");
    }
    return res.toString();
  }

  private String trimOriginalSpaces(Appendable nonTrimmed) {
    StringBuilder res = new StringBuilder();
    String[] lines = this.appendable.toString().split("\n");
    for (String l: lines) {
      res.append(l.trim() + "\n");
    }
    return res.toString();
  }
  private void addToStringBuilder(String text) {
    try {
      this.appendable.append(text);
    } catch (IOException e) {
      throw new IllegalStateException("Can't write to buffer");
    }
  }

  @Override
  public void render() {
    System.out.println(this.appendable.toString());
  }
}