package model.testdouble;

import java.util.Map;
import model.Team;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Horse;
import model.piece.Piece;

public class SpyBoard extends Board {

    // pickPiece 반환용
    private final Piece piece;
    public boolean isMoved = false;

    public SpyBoard(Piece piece) {
        super(Map.of());
        this.piece = piece;
    }

    public static SpyBoard cho() {
        return new SpyBoard(new Horse(Team.CHO));
    }

    public static SpyBoard han() {
        return new SpyBoard(new Horse(Team.HAN));
    }

    @Override
    public void move(Position current, Position next) {
        isMoved = true;
    }

    @Override
    public Piece pickPiece(Position position) {
        return piece;
    }
}
