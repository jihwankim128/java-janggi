package model.piece;

import model.Team;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff >= 1 && rowDiff == 0) || (colDiff == 0 && rowDiff >= 1);
    }
}
