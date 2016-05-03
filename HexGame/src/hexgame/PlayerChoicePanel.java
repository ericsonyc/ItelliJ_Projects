package hexgame;

 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerChoicePanel extends JPanel implements ActionListener {

    private JLabel playerTypeLabel;
    //    private JLabel argsLabel = new JLabel("with args:");
    private JComboBox playerTypeList = new JComboBox(PlayerInterface.playerList);
//    private JTextField argsTextField;

    public PlayerChoicePanel(String name) {
        super();
        playerTypeLabel = new JLabel(name + " Player Type:");
        playerTypeList.setSelectedIndex(0);
//        argsTextField = new JTextField(7);
//        argsTextField.setText(Player.argsList[playerTypeList.getSelectedIndex()]);

        playerTypeList.setActionCommand("player_type");
        playerTypeList.addActionListener(this);
        setup();
    }

    private void setup() {
        this.add(playerTypeLabel);
        this.add(playerTypeList);
//        this.add(argsLabel);
//        this.add(argsTextField);
    }

    public String[] getArgs() {
//        return argsTextField.getText().split(" ");
        return PlayerInterface.argsList[playerTypeList.getSelectedIndex()].split(" ");
    }

    public int getPlayerType() {
        return PlayerInterface.playerIndex[playerTypeList.getSelectedIndex()];
    }

    public void actionPerformed(ActionEvent e) {
        if ("player_type".equals(e.getActionCommand())) {
            System.out.println(e.getActionCommand());

//            argsTextField.setText(Player.argsList[playerTypeList.getSelectedIndex()]);
        }
    }

}
