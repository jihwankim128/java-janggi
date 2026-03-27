package model.piece;

import model.Team;
import model.coordinate.MovablePositions;
import model.coordinate.Position;

public abstract class Piece {

    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public boolean isSameTeam(Piece other) {
        return !isEnemy(other.team);
    }

    public boolean isEnemy(Team team) {
        return this.team != team;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }

    public abstract MovablePositions extractPath(Position current, Position next);

    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = next.calculateColDiff(current);
        return comparePosition(Math.abs(rowDiff), Math.abs(colDiff));
    }

    public boolean isCho() {
        return getTeam() == Team.CHO;
    }

    public boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    protected abstract boolean comparePosition(int rowDiff, int colDiff);
}
