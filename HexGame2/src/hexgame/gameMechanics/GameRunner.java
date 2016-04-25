package hexgame.gameMechanics;

import hexgame.hexBoards.BoardInterface;
import hexgame.hexBoards.BoardInterface;
import hexgame.hexBoards.Board;
import hexgame.players.AdjPlayer;
import hexgame.players.AdjSeasonPlayer;
import hexgame.players.PlayerInterface;
import hexgame.players.HumanPlayer;
import hexgame.players.randomTurn.R_Path;
import hexgame.players.randomTurn.R_Single;

import javax.swing.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRunner extends Thread implements Runner {

    private Board board;
    private PlayerInterface red;
    private PlayerInterface blue;
    private int currentPlayer = BoardInterface.RED;
    private boolean finished = false;
    private volatile boolean stop = false;
    private SeasonMechanics seasonPicker;
    private int gameType;
    private String commentary = "";


    public GameRunner(int boardSize, int gameType, int seasoncount, int redPlayer, String[] redArgs, int bluePlayer, String[] blueArgs) {
        this.seasonPicker = new SeasonMechanics(seasoncount);
        this.board = new Board(boardSize, seasonPicker);
        this.gameType = gameType;
        this.red = createPlayer(redPlayer, BoardInterface.RED, redArgs);
        this.blue = createPlayer(bluePlayer, BoardInterface.BLUE, blueArgs);
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void run() {

        Random coinflip = new Random();

        if (this.gameType == Runner.RANDOM_TURN){
            if (coinflip.nextBoolean() == true)
                this.currentPlayer = BoardInterface.BLUE;
            else
                this.currentPlayer = BoardInterface.RED;
        }
        if(this.gameType==Runner.OPPOSITE_TURN){
            this.currentPlayer=BoardInterface.BLUE;
        }

        /*
         * Main running loop
         */
        while (!finished && !stop) {

            boolean moveAccepted = false;

            Move move = null;
            switch (currentPlayer) {
                case BoardInterface.RED:
                    seasonPicker.thinkingPlayer(BoardInterface.RED);
                    move = red.getMove();
                    break;
                case BoardInterface.BLUE:
                    seasonPicker.thinkingPlayer(BoardInterface.BLUE);
                    move = blue.getMove();
                    break;
                default:
                    System.err.println("invoking mystery player");
                    System.exit(1);
                    break;
            }
            try {
                if(move!=null)
                    moveAccepted = board.makeMove(move);
            } catch (InvalidMoveException ex) {
                Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!moveAccepted)
                System.out.println("Move was not accepted, passing on...");


      /*
       * move has been accepted
       */

            if (board.checkwin(currentPlayer)) {
                notifyWin(currentPlayer);
                finished = true;
            }


            switch (currentPlayer) {
                case BoardInterface.RED:
                    seasonPicker.increment(BoardInterface.RED);
                    this.currentPlayer = BoardInterface.BLUE;
                    break;
                case BoardInterface.BLUE:
                    seasonPicker.increment(BoardInterface.BLUE);
                    this.currentPlayer = BoardInterface.RED;
                    break;
                default:
                    System.err.println("invoking mystery player");
                    System.exit(1);
                    break;
            }
        }
    }

    public void notifyWin(int player) {
        this.finished = true;
        java.awt.Toolkit.getDefaultToolkit().beep();
        switch (player) {
            case BoardInterface.RED:
                System.out.println("Red wins!");
                JOptionPane.showMessageDialog(null, "Red wins!", "Win Dialog", JOptionPane.PLAIN_MESSAGE);
                announce("Red Wins!");
                break;
            case BoardInterface.BLUE:
                System.out.println("Blue wins!");
                JOptionPane.showMessageDialog(null, "Blue wins!", "Win Dialog", JOptionPane.PLAIN_MESSAGE);
                announce("Blue Wins");
                break;
        }
    }

    public void stopGame() {
        stop = true;
        System.out.println("Stopped!");
    }

    public SeasonMechanics getSeasonPicker() {
        return seasonPicker;
    }

    private PlayerInterface createPlayer(int type, int colour, String[] args) {
        PlayerInterface player = null;
        switch (type) {
            case PlayerInterface.R_PATH:
                player = new R_Path(this, colour, args);
                break;
            case PlayerInterface.CLICK_PLAYER:
                player = new HumanPlayer(this, colour);
                break;
            case PlayerInterface.R_POINT:
                player = new R_Single(this, colour, args);
                break;
            case PlayerInterface.ALL_PATH:
                player = new AdjPlayer(this, colour, args);
                break;
            case PlayerInterface.SEASON_PATH:
                player = new AdjSeasonPlayer(this, colour, args);
                break;
            default:
                System.out.println("ERROR - no player or exception");
                break;
        }
        return player;
    }

    public PlayerInterface getPlayerBlue() {
        return blue;
    }

    public PlayerInterface getPlayerRed() {
        return red;
    }

    private void announce(String announcement) {
        this.commentary = announcement;
    }

    public String getCommentary() {
        return commentary;
    }
}
