package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.coordinate.Position;
import model.piece.Piece;

public class Board {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(Position current, Position next) {
        Piece piece = pickPiece(current);
        findByPosition(next).ifPresent(piece::validateTarget);

        board.remove(current);
        board.put(next, piece);
    }

    public Piece pickPiece(Position position) {
        return findByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다."));
    }

    public void arrangePieces(Map<Position, Piece> pieces) {
        board.putAll(pieces);
    }

    public List<Piece> extractPiecesByPath(List<Position> path) {
        return path.stream()
                .filter(this::hasPieceAt)
                .map(this::pickPiece)
                .toList();
    }

    private Optional<Piece> findByPosition(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    private boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }
}
