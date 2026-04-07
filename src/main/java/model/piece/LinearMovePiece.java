package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;

public abstract class LinearMovePiece extends Piece {
    protected LinearMovePiece(Team team, PieceType type) {
        super(team, type);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        validatePalaceDiagonal(current, next);
        validateStraight(current, displacement);
    }

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        if (isPalaceDiagonal(start)) {
            return extractDiagonalPath(start, end);
        }
        return extractLinearPath(start, end);
    }

    private void validateStraight(Position current, Displacement displacement) {
        if (!isPalaceDiagonal(current) && displacement.isNotStraight()) {
            throw new IllegalArgumentException("현재 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private void validatePalaceDiagonal(Position current, Position next) {
        if (isPalaceDiagonal(current) && !isPalaceDiagonal(next)) {
            throw new IllegalArgumentException("궁성 영역 대각선에서 이동 할 수 없는 위치입니다.");
        }
    }

    private List<Position> extractLinearPath(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        Direction direction = displacement.extractCardinal();
        return direction.pathTo(start, end);
    }

    private List<Position> extractDiagonalPath(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        Direction direction = displacement.extractDiagonal();
        return direction.pathTo(start, end);
    }
}
