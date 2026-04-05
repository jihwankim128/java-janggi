package model.piece;

import java.util.List;
import model.Team;
import model.board.Position;
import model.movement.Displacement;

public class Cannon extends Piece {

    private static final int CANNON_HURDLE_COUNT = 1;

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    public void validatePathCondition(List<Piece> pieces) {
        if (pieces.size() != CANNON_HURDLE_COUNT) {
            throw new IllegalArgumentException("포는 정확히 하나의 기물을 뛰어넘어야 합니다.");
        }

        boolean hasCannonAsHurdle = pieces.stream().anyMatch(Piece::isCannon);
        if (hasCannonAsHurdle) {
            throw new IllegalArgumentException("포는 포를 다리로 쓸 수 없습니다.");
        }
    }

    @Override
    public void validateTarget(Piece otherPiece) {
        super.validateTarget(otherPiece);
        if (otherPiece.isCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStraight()) {
            throw new IllegalArgumentException("포가 이동할 수 없는 위치입니다.");
        }
    }
}
