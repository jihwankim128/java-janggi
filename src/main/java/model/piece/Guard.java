package model.piece;

import model.Team;

public class Guard extends Piece {

    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }
}
