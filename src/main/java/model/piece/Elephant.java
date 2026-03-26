package model.piece;

import model.coordinate.Direction;
import model.coordinate.MovablePositions;
import model.coordinate.Position;
import model.Team;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    public MovablePositions extractPath(Position current, Position next) {
        List<Direction> directions = Direction.decomposeToCardinalAndDiagonal(current, next);
        List<Position> path = new ArrayList<>();
        Position step = current;
        for (int i = 0; i < directions.size() - 1; i++) {
            step = step.move(directions.get(i));
            path.add(step);
        }
        return new MovablePositions(path);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff == 3 && rowDiff == 2) || (colDiff == 2 && rowDiff == 3);
    }
}
