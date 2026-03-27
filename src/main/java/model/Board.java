package model;

import model.coordinate.MovablePositions;
import model.coordinate.Position;
import model.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record Board(Map<Position, Piece> board) {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    @Override
    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    public void movePiece(Position current, Position next) {
        Piece piece = pickPiece(current);
        Optional.ofNullable(board.get(next))
                .ifPresent(otherPiece -> checkAlly(piece, otherPiece));

        board.remove(current);
        board.put(next, piece);
    }

    public Piece pickPiece(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다.");
        }
        return board.get(position);
    }

    private void checkAlly(Piece piece, Piece otherPiece) {
        if (piece.isSameTeam(otherPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군이 존재하는 위치입니다.");
        }
    }

    public int countPiecesAt(MovablePositions positions) {
        int count = 0;
        for (Position position : positions) {
            if (hasPieceAt(position)) {
                count++;
            }
        }
        return count;
    }

    private boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    public boolean hasPieceAt(MovablePositions positions) {
        for (Position position : positions) {
            if (hasPieceAt(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCannon(MovablePositions positions) {
        for (Position position : positions) {
            if (!hasPieceAt(position)) {
                continue;
            }
            Piece piece = pickPiece(position);
            if (piece.isCannon()) {
                return true;
            }
        }
        return false;
    }
}
