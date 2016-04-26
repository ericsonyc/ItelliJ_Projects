package hexgame.gameMechanics;

import hexgame.hexBoards.Board;
import hexgame.players.PlayerInterface;

public interface Runner {
    public static final int FAIR_TURN = 0;
    public static final int OPPOSITE_TURN=1;
    public static final int RANDOM_TURN = 2;

    public static final String[] GAME_LIST = {"Red First","Blue First", "Random Turn"};
    public static final int[] GAME_CODES = {FAIR_TURN,OPPOSITE_TURN, RANDOM_TURN};

    public Board getBoard();

    public PlayerInterface getPlayerRed();

    public PlayerInterface getPlayerBlue();

    public void stopGame();

    public SeasonMechanics getSeasonPicker();

    public String getCommentary();
}
