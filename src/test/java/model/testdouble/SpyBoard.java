package model.testdouble;

import java.util.Map;
import model.Team;
import model.board.Board;
import model.coordinate.Position;
import model.piece.General;
import model.piece.Piece;

public class SpyBoard extends Board {

    public boolean isMoved = false;

    public SpyBoard(Map<Position, Piece> pieces) {
        super(pieces);
    }

    public static SpyBoard withBothGenerals() {
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 4), new General(Team.HAN),
                new Position(8, 4), new General(Team.CHO)
        );
        return new SpyBoard(pieces);
    }

    public static SpyBoard withOnlyHanGeneral() {
        return new SpyBoard(Map.of(new Position(1, 4), new General(Team.HAN)));
    }

    public static SpyBoard withOnlyChoGeneral() {
        return new SpyBoard(Map.of(new Position(8, 4), new General(Team.CHO)));
    }

    @Override
    public void move(Position current, Position next) {
        isMoved = true;
        super.move(current, next);
    }
}
