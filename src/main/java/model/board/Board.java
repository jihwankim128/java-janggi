package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.piece.Piece;

public class Board {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;
    private static final int CANNON_HURDLE_COUNT = 1;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(Position current, Position next) {
        Piece piece = pickPiece(current);
        if (!piece.canMove(current, next)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }

        findByPosition(next).ifPresent(otherPiece -> {
            if (otherPiece.isSameTeam(piece)) {
                throw new IllegalArgumentException("아군이 있는 위치로 이동할 수 없습니다.");
            }
        });

        List<Position> path = piece.extractPath(current, next);
        if (piece.isCannon()) {
            int countedPieces = countPiecesAt(path);
            if (countedPieces != CANNON_HURDLE_COUNT) {
                throw new IllegalArgumentException("포는" + CANNON_HURDLE_COUNT + "개의 기물만 건너 뛰어야 합니다.");
            }
            if (hasCannon(path)) {
                throw new IllegalArgumentException("포는 포를 건너뛸 수 없습니다.");
            }
            board.remove(current);
            board.put(next, piece);
            return;
        }

        if (hasPieceAt(path)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없는 위치입니다.");
        }

        board.remove(current);
        board.put(next, piece);
    }

    public Piece pickPiece(Position position) {
        return findByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다."));
    }

    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    private Optional<Piece> findByPosition(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    private int countPiecesAt(List<Position> path) {
        return (int) path.stream()
                .filter(this::hasPieceAt)
                .count();
    }

    private boolean hasPieceAt(List<Position> path) {
        return path.stream()
                .anyMatch(this::hasPieceAt);
    }

    private boolean hasCannon(List<Position> path) {
        return path.stream()
                .filter(this::hasPieceAt)
                .anyMatch(position -> pickPiece(position).isCannon());
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }
}
