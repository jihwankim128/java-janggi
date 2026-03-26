package model.piece;

import model.Team;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        return (colDiff == 3 && rowDiff == 2) || (colDiff == 2 && rowDiff == 3);
    }
}
