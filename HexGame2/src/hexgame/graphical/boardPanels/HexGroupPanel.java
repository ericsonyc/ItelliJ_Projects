package hexgame.graphical.boardPanels;

import hexgame.hexBoards.ScoreBoard;
import hexgame.players.PlayerInterface;

import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class HexGroupPanel extends JTabbedPane {

  PlayerInterface PlayerInterface;

  public HexGroupPanel(PlayerInterface PlayerInterface) {
    super();
    this.PlayerInterface = PlayerInterface;
    setup();
  }

  private void setup() {

    for (int i = 0; i < PlayerInterface.getAuxBoards().size(); i++) {
      HexPanel panel;
      if (PlayerInterface.getAuxBoards().get(i) instanceof ScoreBoard)
        panel = new HeatMap((ScoreBoard) PlayerInterface.getAuxBoards().get(i));
      else
        panel = new HexDisplayOnlyPanel(PlayerInterface.getAuxBoards().get(i));
      this.addTab(panel.getName(), panel);
      panel.startAnimation();
    }
  }
}
