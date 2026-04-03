package model.piece;

import model.Team;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff >= 1 && rowDiff == 0) || (colDiff == 0 && rowDiff >= 1);
    }
}
