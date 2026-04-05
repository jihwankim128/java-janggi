package model;

import java.util.List;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;

public class JanggiGame {

    private final Board board;
    private Team turn;

    public JanggiGame(Board board) {
        this.board = board;
        this.turn = Team.CHO;
    }

    public void movePiece(Position current, Position next) {
        Piece piece = selectPiece(current);

        List<Position> path = piece.pathTo(current, next);
        List<Piece> pieces = board.extractPiecesByPath(path);

        piece.validatePathCondition(pieces);
        board.move(current, next);

        this.turn = turn.next();
    }

    public Piece selectPiece(Position position) {
        Piece piece = board.pickPiece(position);
        validateAlly(piece);
        return piece;
    }

    private void validateAlly(Piece piece) {
        if (!piece.isSameTeam(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
    }

    public Team getTurn() {
        return turn;
    }
}
