package model.piece;

import model.Team;
import model.coordinate.Displacement;
import model.coordinate.Position;

public abstract class PalacePiece extends Piece {
    protected PalacePiece(Team team, PieceType type) {
        super(team, type);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        validatePalacePosition(current, next);
        validateOneStep(displacement);
        validateDiagonal(current, displacement);
    }

    private void validatePalacePosition(Position current, Position next) {
        if (isNotPalace(current) || isNotPalace(next)) {
            throw new IllegalArgumentException("궁성 영역 내부에서만 이동할 수 있습니다.");
        }
    }

    private void validateOneStep(Displacement displacement) {
        if (!displacement.isOneStepInRange()) {
            throw new IllegalArgumentException("궁성 내부에서 이동할 수 없는 위치입니다.");
        }
    }

    private void validateDiagonal(Position current, Displacement displacement) {
        if (displacement.isNotStraight() && !isPalaceDiagonal(current)) {
            throw new IllegalArgumentException("궁성 대각선 이동은 특정 지점에서만 가능합니다.");
        }
    }
}