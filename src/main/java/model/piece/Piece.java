package model.piece;

import java.util.List;
import model.Team;
import model.board.Position;

public abstract class Piece {

    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public List<Position> extractPath(Position current, Position next) {
        validateMove(current, next);
        return type.extractPath(current, next);
    }

    public boolean isOtherTeam(Team team) {
        return this.team != team;
    }

    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = next.calculateColDiff(current);
        return comparePosition(Math.abs(rowDiff), Math.abs(colDiff));
    }

    public void validatePathCondition(List<Piece> pieces) {
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없는 위치입니다.");
        }
    }

    public void validateTarget(Piece otherPiece) {
        if (getTeam() == otherPiece.team) {
            throw new IllegalArgumentException("아군이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public void validateMove(Position current, Position next) {
        if (!canMove(current, next)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    protected abstract boolean comparePosition(int rowDiff, int colDiff);

    protected boolean isCho() {
        return !team.isHan();
    }

    protected boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }
}
