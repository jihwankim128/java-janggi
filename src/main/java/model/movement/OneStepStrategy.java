package model.movement;

import java.util.List;
import model.board.Position;

public class OneStepStrategy implements MoveStrategy {

    @Override
    public List<Position> extractPath(Position start, Position end) {
        Displacement displacement = end.minus(start);
        Direction cardinal = displacement.extractCardinal();

        Position step = cardinal.move(start);
        return List.of(step);
    }
}