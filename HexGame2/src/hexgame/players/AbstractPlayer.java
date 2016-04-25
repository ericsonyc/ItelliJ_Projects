package hexgame.players;

import hexgame.gameMechanics.Runner;
import hexgame.hexBoards.BoardInterface;

import java.util.ArrayList;

public abstract class AbstractPlayer implements PlayerInterface {

    protected ArrayList<BoardInterface> auxBoards = new ArrayList<BoardInterface>();
    protected Runner game;
    protected int player;
    protected int opponent;
    protected int size;

    public AbstractPlayer(Runner game, int colour, String[] args) {
        this.game = game;
        this.player = colour;
        if (this.player == BoardInterface.RED)
            this.opponent = BoardInterface.BLUE;
        else
            this.opponent = BoardInterface.RED;
        this.size = game.getBoard().getSize();
    }

    public ArrayList<BoardInterface> getAuxBoards() {
        return auxBoards;
    }
}
