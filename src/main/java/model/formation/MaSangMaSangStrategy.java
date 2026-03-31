package model.formation;

import static model.board.Position.CHO_LEFT_INNER;
import static model.board.Position.CHO_LEFT_OUTER;
import static model.board.Position.CHO_RIGHT_INNER;
import static model.board.Position.CHO_RIGHT_OUTER;
import static model.board.Position.HAN_LEFT_INNER;
import static model.board.Position.HAN_LEFT_OUTER;
import static model.board.Position.HAN_RIGHT_INNER;
import static model.board.Position.HAN_RIGHT_OUTER;

import java.util.Map;
import model.Team;
import model.board.Position;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.Piece;

public class MaSangMaSangStrategy extends FormationStrategy {
    @Override
    protected Map<Position, Piece> generateHanFormation() {
        return Map.of(
                HAN_LEFT_OUTER, new Horse(Team.HAN),
                HAN_LEFT_INNER, new Elephant(Team.HAN),
                HAN_RIGHT_INNER, new Horse(Team.HAN),
                HAN_RIGHT_OUTER, new Elephant(Team.HAN)
        );
    }

    @Override
    protected Map<Position, Piece> generateChoFormation() {
        return Map.of(
                CHO_LEFT_OUTER, new Horse(Team.CHO),
                CHO_LEFT_INNER, new Elephant(Team.CHO),
                CHO_RIGHT_INNER, new Horse(Team.CHO),
                CHO_RIGHT_OUTER, new Elephant(Team.CHO)
        );
    }
}
