package hexgame;

 

public abstract class AbstractPlayer implements PlayerInterface {

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

}
