package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    public Piece pickPiece(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다.");
        }
        return board.get(position);
    }

    public void movePiece(Position current, Position next) {
        Piece piece = pickPiece(current);
        Optional.ofNullable(board.get(next))
                .ifPresent(otherPiece -> checkAlly(piece, otherPiece));

        board.remove(current);
        board.put(next, piece);
    }

    private void checkAlly(Piece piece, Piece otherPiece) {
        if (piece.isSameTeam(otherPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군이 존재하는 위치입니다.");
        }
    }
}
