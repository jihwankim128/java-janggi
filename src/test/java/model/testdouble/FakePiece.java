package model.testdouble;

import java.util.List;
import model.Team;
import model.board.Position;
import model.piece.Piece;
import model.piece.PieceType;

public class FakePiece extends Piece {

    private final boolean movable;
    private final List<Position> path;

    FakePiece(Team team, PieceType type, boolean movable, List<Position> path) {
        super(team, type);
        this.movable = movable;
        this.path = path;
    }

    public static FakePiece createFake(Team team) {
        return new FakePiece(team, PieceType.SOLDIER, true, List.of());
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return movable;
    }

    @Override
    public List<Position> extractPath(Position current, Position next) {
        return path;
    }
}
