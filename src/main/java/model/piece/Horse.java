package model.piece;

import model.Team;
import model.board.Position;
import model.movement.Displacement;

public class Horse extends Piece {

    private static final int HORSE_LONG_STEP = 2;
    private static final int HORSE_SHORT_STEP = 1;

    public Horse(Team team) {
        super(team, PieceType.HORSE);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStepCombination(HORSE_LONG_STEP, HORSE_SHORT_STEP)) {
            throw new IllegalArgumentException("마가 이동할 수 없는 위치입니다.");
        }
    }
}
