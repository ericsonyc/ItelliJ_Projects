package hexgame.players;


import hexgame.gameMechanics.Move;
import hexgame.gameMechanics.Runner;
import hexgame.hexBoards.BoardInterface;

import java.awt.Point;
import java.util.ArrayList;


public class HumanPlayer implements PlayerInterface {
    private Runner game = null;
    private int colour = 0;
    public static boolean abstention=false;
    public HumanPlayer(Runner game, int colour) {
        this.game = game;
        this.colour = colour;
    }

    public Move getMove() {
        switch (colour) {
            case BoardInterface.RED:
                System.out.print("Red move: ");
                break;
            case BoardInterface.BLUE:
                System.out.print("Blue move: ");
                break;
        }

        while (game.getBoard().getSelected() == null&&!abstention) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!abstention) {
            Point choice = game.getBoard().getSelected();

            Move move = new Move(colour, choice.x, choice.y);

            game.getBoard().setSelected(null);
            return move;
        }else{
            abstention=false;
            return null;
        }
    }

    public ArrayList<BoardInterface> getAuxBoards() {
        return new ArrayList<BoardInterface>();
    }
}
