package model.piece;

import model.Team;
import model.coordinate.Displacement;
import model.coordinate.Position;

public class Soldier extends Piece {

    private final int forwardDirection;

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
        this.forwardDirection = resolveForwardDirection(team);
    }

    private static int resolveForwardDirection(Team team) {
        if (team.isHan()) {
            return 1;
        }
        return -1;
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (!(displacement.isForwardBy(forwardDirection) || displacement.isSideOneStep())) {
            throw new IllegalArgumentException("현재 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
