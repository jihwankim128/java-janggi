package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Position;

public abstract class Piece {

    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public abstract List<Position> extractPath(Position current, Position next);

    public boolean isSameTeam(Piece other) {
        return !isEnemy(other.team);
    }

    public boolean isEnemy(Team team) {
        return this.team != team;
    }

    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = next.calculateColDiff(current);
        return comparePosition(Math.abs(rowDiff), Math.abs(colDiff));
    }

    protected abstract boolean comparePosition(int rowDiff, int colDiff);

    public boolean isCho() {
        return getTeam() == Team.CHO;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    public PieceType getType() {
        return type;
    }
}
