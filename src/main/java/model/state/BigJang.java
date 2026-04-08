package model.state;

import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class BigJang extends JanggiState {

    public BigJang(Team turn) {
        super(turn);
    }

    @Override
    public Team resolveWinner(Board board) {
        throw new IllegalStateException("빅장 합의가 필요합니다.");
    }

    @Override
    public boolean canPlaying() {
        return true;
    }

    @Override
    public GameStatus status() {
        return GameStatus.BIG_JANG;
    }
}