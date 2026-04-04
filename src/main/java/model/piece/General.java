package model.piece;

import model.Team;
import model.board.Position;

public class General extends Piece {

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }
}
