package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;

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

        boolean hasCannonAsHurdle = pieces.stream().anyMatch(piece -> piece.isSameType(this));
        if (hasCannonAsHurdle) {
            throw new IllegalArgumentException("포는 포를 다리로 쓸 수 없습니다.");
        }
    }

    @Override
    public void validateTarget(Piece otherPiece) {
        super.validateTarget(otherPiece);
        if (otherPiece.isSameType(this)) {
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

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        Direction direction = resolveCardinal(start, end);

        List<Position> path = new ArrayList<>();
        Position step = direction.move(start);
        while (!step.equals(end)) {
            path.add(step);
            step = direction.move(step);
        }
        return path;
    }

    private Direction resolveCardinal(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        return displacement.extractCardinal();
    }
}
