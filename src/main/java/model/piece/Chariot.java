package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStraight()) {
            throw new IllegalArgumentException("차가 이동할 수 없는 위치입니다.");
        }
    }

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        Direction direction = resolveCardinal(start, end);

        List<Position> path = new ArrayList<>();
        Position step = direction.move(start);
        while (!step.equals(end)) {
            path.add(step);
            step = direction.move(step);
        }
        return path;
    }

    private Direction resolveCardinal(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        return displacement.extractCardinal();
    }
}
