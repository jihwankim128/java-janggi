package model;

import java.util.HashMap;
import java.util.Map;
import model.piece.Piece;

public class Board {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
