package model.piece;

import model.Team;

public abstract class Piece {

    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }
}
