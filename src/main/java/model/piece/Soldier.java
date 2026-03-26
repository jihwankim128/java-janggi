package model.piece;

import static model.Team.CHO;

import model.Position;
import model.Team;

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
        if (getTeam() == CHO) {
            return (rowDiff == -1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
        }
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
}
