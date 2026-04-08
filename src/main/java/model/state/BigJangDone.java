package model.state;

import model.GameStatus;
import model.JanggiReferee;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class BigJangDone extends JanggiState {

    private static final double AFTER_TURN_BONUS_SCORE = 1.5;

    public BigJangDone(Team turn) {
        super(turn);
    }

    @Override
    public Team resolveWinner(Board board) {
        return JanggiReferee.judgeWinner(board);
    }

    @Override
    public boolean canPlaying() {
        return false;
    }

    @Override
    public GameStatus status() {
        return GameStatus.BIG_JANG_DONE;
    }
}