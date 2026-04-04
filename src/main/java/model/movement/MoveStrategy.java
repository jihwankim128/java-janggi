package model.movement;

import java.util.List;
import model.board.Position;

public interface MoveStrategy {

    List<Position> extractPath(Position start, Position end);
}
