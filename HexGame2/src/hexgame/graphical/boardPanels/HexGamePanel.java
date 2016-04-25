package hexgame.graphical.boardPanels;

import hexgame.hexBoards.Board;
import hexgame.hexBoards.BoardInterface;
import hexgame.hexBoards.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;

@SuppressWarnings("serial")
public class HexGamePanel extends HexPanel{


  public HexGamePanel(Board board){
    super(board);
    this.addMouseListener(new HexSelector());
    this.setPreferredSize(new Dimension(400, 200));
  }





  @Override
  	public Color getFillColour(int x, int y) {
     int value = board.get(x, y);
		Color returnColour = Color.WHITE;
		switch (value) {
		case BoardInterface.RED:
			returnColour = (Color.RED);
			break;
		case BoardInterface.BLUE:
			returnColour = (Color.BLUE);
			break;
		case BoardInterface.BLANK:
      if(((Board)board).getNumberOfSeasons()>1)
        returnColour = ((Board)board).getSeasonColour(x, y);
      else
        returnColour = Color.WHITE;
			break;
		default:
			returnColour = (Color.WHITE);
		}
		return returnColour;
	}


  @Override
	public Color getSeasonColour(int value) {
		Color returnColour = Color.WHITE;
		switch (value) {
		case 0:
			returnColour = (Color.GREEN.brighter());
			break;
		case 1:
			returnColour = (Color.YELLOW.brighter());
			break;
		case 2:
			returnColour = (Color.magenta.brighter());
			break;
		case 3:
			returnColour = (Color.PINK);
			break;
		default:
			returnColour = (Color.WHITE);
		}
		return returnColour;
	}

public void click(Point p) {
		chosenXY = p;
		row: for (int y = 0; y < size; y++) {
		column:	for (int x = 0; x < size; x++) {
        	Polygon poly = calcHexPoly(x, y);
        	if(poly.contains(p)){
        		((Board)board).setSelected(x,y);
        		break row;
        	}
			}
		}
	}

}
