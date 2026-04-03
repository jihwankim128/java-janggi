package model.piece;

import model.Team;
import model.board.Position;
import model.movement.Displacement;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.minus(current);
        if (displacement.isNotStraight()) {
            throw new IllegalArgumentException("차가 이동할 수 없는 위치입니다.");
        }
    }
}
