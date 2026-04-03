package model.board;

import static model.board.Board.BOARD_COL;
import static model.board.Board.BOARD_ROW;

import model.movement.Displacement;

public record Position(int row, int col) {

    public Position {
        if (row < 0 || row >= BOARD_ROW) {
            throw new IllegalArgumentException("장기판의 행 범위를 벗어났습니다. 최대 범위: " + BOARD_ROW);
        }

        if (col < 0 || col >= BOARD_COL) {
            throw new IllegalArgumentException("장기판의 열 범위를 벗어났습니다. 최대 범위: " + BOARD_COL);
        }
    }

    public Displacement minus(Position other) {
        return new Displacement(calculateRowDiff(other), calculateColDiff(other));
    }

    public int calculateRowDiff(Position other) {
        return this.row() - other.row();
    }

    public int calculateColDiff(Position other) {
        return this.col - other.col();
    }

    public Position resolveNext(int rowDistance, int colDistance) {
        return new Position(this.row + rowDistance, this.col + colDistance);
    }
}
