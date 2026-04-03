package model.piece;

import model.Team;
import model.board.Position;
import model.movement.Displacement;

public class Soldier extends Piece {

    private static final int SOLDIER_FORWARD_STEP = 1;

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.minus(current);
        int forwardCount = resolveForwardCount();

        if (!(displacement.isForwardBy(forwardCount) || displacement.isSideOneStep())) {
            throw new IllegalArgumentException("졸이 이동할 수 없는 위치입니다.");
        }
    }

    private int resolveForwardCount() {
        if (isCho()) {
            return -SOLDIER_FORWARD_STEP;
        }
        return SOLDIER_FORWARD_STEP;
    }

}
