package model.piece;

import model.Team;
import model.board.Position;
import model.movement.Displacement;

public class Elephant extends Piece {

    private static final int ELEPHANT_LONG_STEP = 3;
    private static final int ELEPHANT_SHORT_STEP = 2;

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStepCombination(ELEPHANT_LONG_STEP, ELEPHANT_SHORT_STEP)) {
            throw new IllegalArgumentException("상이 이동할 수 없는 위치입니다.");
        }
    }
}
