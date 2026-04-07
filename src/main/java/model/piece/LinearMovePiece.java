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
        if (displacement.isNotStraight()) {
            validatePalaceDiagonal(current, next);
        }
    }

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        if (isPalaceDiagonal(start)) {
            return extractDiagonalPath(start, end);
        }
        return extractLinearPath(start, end);
    }

    private void validatePalaceDiagonal(Position current, Position next) {
        if (!isPalaceDiagonal(current) || !isPalaceDiagonal(next)) {
            throw new IllegalArgumentException("대각선 이동은 궁성 교차점에서만 가능합니다.");
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
