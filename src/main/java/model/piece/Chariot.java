package model.piece;

import model.Team;
import model.coordinate.Direction;
import model.coordinate.MovablePositions;
import model.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    public MovablePositions extractPath(Position current, Position next) {
        Direction direction = Direction.from(current, next);
        List<Position> path = new ArrayList<>();
        Position step = current.move(direction);
        while (!step.equals(next)) {
            path.add(step);
            step = step.move(direction);
        }
        return new MovablePositions(path);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff >= 1 && rowDiff == 0) || (colDiff == 0 && rowDiff >= 1);
    }
}
