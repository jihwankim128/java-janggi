package model.state;

import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class Finished extends JanggiState {

    public Finished(Team winner) {
        super(winner);
    }

    @Override
    public Team resolveWinner(Board board) {
        return turn();
    }

    @Override
    public boolean canPlaying() {
        return false;
    }

    @Override
    public GameStatus status() {
        return GameStatus.DONE;
    }
}