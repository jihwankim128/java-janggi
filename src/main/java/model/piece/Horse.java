package model.piece;

import model.Team;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team, PieceType.HORSE);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff == 1 && rowDiff == 2) || (colDiff == 2 && rowDiff == 1);
    }
}
