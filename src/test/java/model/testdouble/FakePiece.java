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

    public static FakePiece 이동_가능(Team team) {
        return new FakePiece(team, PieceType.SOLDIER, true, List.of());
    }

    public static FakePiece 이동_불가(Team team) {
        return new FakePiece(team, PieceType.SOLDIER, false, List.of());
    }

    public static FakePiece 이동_가능한_포(Team team, List<Position> path) {
        return new FakePiece(team, PieceType.CANNON, true, path);
    }

    public static FakePiece 이동_가능하지만_경로_있는_기물(Team team, List<Position> path) {
        return new FakePiece(team, PieceType.SOLDIER, true, path);
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
