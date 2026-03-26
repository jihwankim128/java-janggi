package model.piece;

import model.coordinate.MovablePositions;
import model.coordinate.Position;
import model.Team;

public class Guard extends Piece {

    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    public MovablePositions extractPath(Position current, Position next) {
        return MovablePositions.empty();
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        throw new IllegalArgumentException("1단계 궁성 영역 미구현");
    }
}
