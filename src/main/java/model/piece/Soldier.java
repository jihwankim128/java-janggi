package model.piece;

import model.Team;
import model.board.Position;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
    }

    @Override
    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = Math.abs(next.calculateColDiff(current));
        return comparePosition(rowDiff, colDiff);
    }

    @Override
    protected boolean comparePosition(int rowDiff, int colDiff) {
        if (isCho()) {
            return (rowDiff == -1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
        }
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
}
