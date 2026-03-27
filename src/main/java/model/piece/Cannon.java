package model.piece;

import model.coordinate.Direction;
import model.coordinate.MovablePositions;
import model.coordinate.Position;
import model.Team;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
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
