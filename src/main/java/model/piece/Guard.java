package model.piece;

import model.Team;
import model.board.Position;

public class Guard extends Piece {

    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }
}
