package hexgame.graphical;

import hexgame.gameMechanics.GameRunner;
import hexgame.gameMechanics.Runner;
import hexgame.graphical.boardPanels.HexGamePanel;
import hexgame.graphical.boardPanels.HexPanel;
import hexgame.hexBoards.BoardInterface;
import hexgame.hexBoards.BoardInterface;
import hexgame.players.HumanPlayer;
import hexgame.players.PlayerInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

@SuppressWarnings("serial")
class Main extends JFrame implements ActionListener {

    private static Main frame;
    private Thread gameThread;
    private JPanel activeBoardsPanel = null;//the second board used to play game
    private JPanel buttonPanel = null;
    private JButton startButton = new JButton("Start");
    private Runner game;
    //    private JPanel auxBoardsPanel;
    private JPanel playBoardPanel;
    private JPanel gameSettings;//initial board, the set of game parameters
    private JPanel settingsPanel;
    private JButton redAbstentionBtn = new JButton("Abstention");
    private JButton blueAbstentionBtn = new JButton("Abstention");
    private PlayerChoicePanel redPlayerOptions;
    private PlayerChoicePanel bluePlayerOptions;
    private BoardSetupPanel boardSettings;

    private Main() {
        //gamesettings: set the players
        gameSettings = new JPanel(new GridLayout(3, 1));
        redPlayerOptions = new PlayerChoicePanel("Red");
        bluePlayerOptions = new PlayerChoicePanel("Blue");
        boardSettings = new BoardSetupPanel();

        gameSettings.add(redPlayerOptions);
        gameSettings.add(bluePlayerOptions);
        gameSettings.add(boardSettings);

        buttonPanel = new JPanel(new GridLayout(2, 1));
        startButton.setMnemonic(KeyEvent.VK_SPACE);
        startButton.setActionCommand("start");//set the command string
        startButton.setEnabled(true);
        startButton.addActionListener(this);

        buttonPanel.add(startButton);
        //settingsPanel used to combine gameSettings and buttonPanel
        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.add(gameSettings, BorderLayout.CENTER);
        settingsPanel.add(buttonPanel, BorderLayout.EAST);

        this.add(settingsPanel, BorderLayout.SOUTH);
    }

    private void prepareGame() {

        int red = redPlayerOptions.getPlayerType();//get the strategy of the red player
        String[] redArgs = redPlayerOptions.getArgs();

        int blue = bluePlayerOptions.getPlayerType();
        String[] blueArgs = bluePlayerOptions.getArgs();
        //get the gameType, the turn of two players
        int gameType = boardSettings.getGameType();
        int boardSize = boardSettings.getBoardSize();
        int numberOfSeasons = boardSettings.getSeasonSize();
        game = new GameRunner(boardSize, gameType, numberOfSeasons, red, redArgs, blue, blueArgs, redAbstentionBtn, blueAbstentionBtn);
        gameThread = (Thread) game;
    }

    public void generateBoardPanels() {
        // if the boardsPanel exists
        if (activeBoardsPanel != null)
            this.remove(activeBoardsPanel);

        activeBoardsPanel = new JPanel();
        activeBoardsPanel.setLayout(new BorderLayout());//set the layout of the activeBoardsPanel

        playBoardPanel = new JPanel();
        playBoardPanel.setLayout(new BorderLayout());

        JPanel tickerPanels = new JPanel();
        tickerPanels.setLayout(new GridLayout(2, 1));

        JPanel redPanel = new JPanel();
        redPanel.add(new JLabel("Red:"));
        TurnViewer redTicker = new TurnViewer(game.getSeasonPicker(), BoardInterface.RED);

        redTicker.startAnimation();
        redPanel.add(redTicker);
        redAbstentionBtn.setActionCommand("red abstention");
        redAbstentionBtn.addActionListener(this);
        redPanel.add(redAbstentionBtn);
        if (redPlayerOptions.getPlayerType() != PlayerInterface.CLICK_PLAYER) {
            redAbstentionBtn.setEnabled(false);
        }
        tickerPanels.add(redPanel);

        JPanel bluePanel = new JPanel();
        bluePanel.add(new JLabel("Blue:"));
        TurnViewer blueTicker = new TurnViewer(game.getSeasonPicker(), BoardInterface.BLUE);

        blueTicker.startAnimation();
        bluePanel.add(blueTicker);
        blueAbstentionBtn.setActionCommand("blue abstention");
        blueAbstentionBtn.addActionListener(this);
        if (bluePlayerOptions.getPlayerType() != PlayerInterface.CLICK_PLAYER) {
            blueAbstentionBtn.setEnabled(false);
        }
        bluePanel.add(blueAbstentionBtn);
        tickerPanels.add(bluePanel);

        HexPanel mainBoardPanel = new HexGamePanel(game.getBoard());
        mainBoardPanel.startAnimation();

        playBoardPanel.add(mainBoardPanel, BorderLayout.CENTER);
        playBoardPanel.add(tickerPanels, BorderLayout.SOUTH);

        activeBoardsPanel.add(playBoardPanel, BorderLayout.CENTER);

        this.add(activeBoardsPanel, BorderLayout.CENTER);

        frame.pack();
    }

    public void actionPerformed(ActionEvent e) {
        //when click on the start button, perform this method
        if ("start".equals(e.getActionCommand())) {
            //if game is starting, first stop the game
            if (game != null)
                game.stopGame();

            this.prepareGame();//prepare the hex game
            generateBoardPanels();//generate the panel of boards
            gameThread.start();
        } else if ("red abstention".equals(e.getActionCommand())) {
            if (redPlayerOptions.getPlayerType() == PlayerInterface.CLICK_PLAYER) {
                HumanPlayer.abstention = true;
            }
        } else if ("blue abstention".equals(e.getActionCommand())) {
            if (bluePlayerOptions.getPlayerType() == PlayerInterface.CLICK_PLAYER) {
                HumanPlayer.abstention = true;
            }
        }
    }

    public static void main(String[] args) {

        frame = new Main();
        frame.setTitle("HexGame");
        WindowListener l = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension gameSize = frame.getSize();
        frame.setLocation((screenSize.width - gameSize.width) / 2, (screenSize.height - gameSize.height) / 2);
        frame.pack();
        frame.setVisible(true);
    }
}
