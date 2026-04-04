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

    protected abstract void validateMove(Position current, Position next);

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
