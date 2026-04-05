package model.movement;

import java.util.List;
import model.board.Position;

public class SteppingStrategy implements MoveStrategy {

    @Override
    public List<Position> extractPath(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        Direction cardinal = displacement.extractCardinal();
        Direction diagonal = displacement.extractDiagonal();

        Position oneStep = cardinal.move(start);
        Position stepping = diagonal.move(oneStep);
        return List.of(oneStep, stepping);
    }
}