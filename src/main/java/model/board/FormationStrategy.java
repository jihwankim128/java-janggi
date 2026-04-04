package model.board;

import java.util.List;
import model.Team;
import model.piece.Piece;

public interface FormationStrategy {

    List<Piece> generateByTeam(Team team);
}
