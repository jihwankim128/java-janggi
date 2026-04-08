package model.state;

import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class Running extends JanggiState {

    public Running(Team turn) {
        super(turn);
    }

    @Override
    public Team resolveWinner(Board board) {
        throw new IllegalStateException("아직 게임이 진행중입니다.");
    }

    @Override
    public boolean canPlaying() {
        return true;
    }

    @Override
    public GameStatus status() {
        return GameStatus.PLAYING;
    }
}