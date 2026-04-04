package model.piece;

import java.util.List;
import model.board.Position;
import model.movement.LinearStrategy;
import model.movement.MoveStrategy;
import model.movement.OneStepStrategy;
import model.movement.SteppingStrategy;

public enum PieceType {

    CANNON(new LinearStrategy()),
    CHARIOT(new LinearStrategy()),
    ELEPHANT(new SteppingStrategy()),
    GENERAL((start, end) -> {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }),
    GUARD((start, end) -> {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }),
    HORSE(new OneStepStrategy()),
    SOLDIER((start, end) -> List.of()),
    ;

    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public List<Position> extractPath(Position current, Position next) {
        return moveStrategy.extractPath(current, next);
    }
}
