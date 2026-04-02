package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Team;
import model.board.Direction;
import model.board.Position;

public class Cannon extends Piece {

    private static final int CANNON_HURDLE_COUNT = 1;

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        Direction direction = Direction.from(current, next);
        List<Position> path = new ArrayList<>();
        Position step = current.move(direction);
        while (!step.equals(next)) {
            path.add(step);
            step = step.move(direction);
        }
        return path;
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
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff >= 1 && rowDiff == 0) || (colDiff == 0 && rowDiff >= 1);
    }
}
