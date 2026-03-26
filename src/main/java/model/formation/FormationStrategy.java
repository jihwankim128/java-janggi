package model.formation;

import java.util.Map;
import model.coordinate.Position;
import model.Team;
import model.piece.Piece;

public abstract class FormationStrategy {

    public Map<Position, Piece> generateFormation(Team team) {
        if (team == Team.HAN) {
            return generateHanFormation();
        }
        return generateChoFormation();
    }

    protected abstract Map<Position, Piece> generateHanFormation();

    protected abstract Map<Position, Piece> generateChoFormation();
}
