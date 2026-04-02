package model;

import model.board.Board;
import model.board.Position;
import model.piece.Piece;

public class JanggiGame {

    private final Board board;
    private Team turn;

    public JanggiGame(Board board) {
        this.board = board;
        this.turn = Team.CHO;
    }

    public void movePiece(Position current, Position next) {
        selectPiece(current);
        board.move(current, next);
        this.turn = turn.next();
    }

    public Piece selectPiece(Position position) {
        Piece piece = board.pickPiece(position);
        validateCurrentTurn(piece);
        return piece;
    }

    private void validateCurrentTurn(Piece piece) {
        if (piece.isOtherTeam(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
    }

    public Team getTurn() {
        return turn;
    }
}
