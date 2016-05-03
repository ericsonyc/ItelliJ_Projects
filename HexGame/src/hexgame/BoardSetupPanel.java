package hexgame;

 

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class BoardSetupPanel extends JPanel {

    private final JLabel sizeLabel;
    private final SpinnerNumberModel boardSizeConstraints;
    private final JSpinner boardSizeSpinner;
    private final JLabel turnStyleLabel;
    private final JComboBox turnStyleList;

    public BoardSetupPanel() {
        super();

        this.sizeLabel = new JLabel("Board size:");
        this.boardSizeConstraints = new SpinnerNumberModel(
                BoardInterface.DEFAULT_BOARD_SIZE,
                BoardInterface.MIN_SUPPORTED_BOARD_SIZE,
                BoardInterface.MAX_SUPPORTED_BOARD_SIZE,
                1);
        this.boardSizeSpinner = new JSpinner(boardSizeConstraints);

        this.turnStyleLabel = new JLabel("  Turn style:");
        this.turnStyleList = new JComboBox(Runner.GAME_LIST);

        setup();
    }

    private void setup() {
        this.add(sizeLabel);
        this.add(boardSizeSpinner);
        this.add(turnStyleLabel);
        this.add(turnStyleList);
    }

    public int getGameType() {
        return Runner.GAME_CODES[turnStyleList.getSelectedIndex()];
    }

    public int getBoardSize() {
        return (Integer) boardSizeSpinner.getValue();
    }

    public int getSeasonSize() {
        return 1;
    }
}
