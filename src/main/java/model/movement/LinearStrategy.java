package model.movement;

import java.util.ArrayList;
import java.util.List;
import model.board.Position;

public class LinearStrategy implements MoveStrategy {

    private static Direction resolveCardinal(Position start, Position end) {
        Displacement displacement = end.minus(start);
        return displacement.extractCardinal();
    }

    @Override
    public List<Position> extractPath(Position start, Position end) {
        Direction direction = resolveCardinal(start, end);

        List<Position> path = new ArrayList<>();
        Position step = direction.move(start);
        while (!step.equals(end)) {
            path.add(step);
            step = direction.move(step);
        }
        return path;
    }
}