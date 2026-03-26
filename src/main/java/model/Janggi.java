package model;

import model.piece.Piece;

public class Janggi {

    private final Board board;
    private Team turn;

    public Janggi(Board board) {
        this.board = board;
        this.turn = Team.CHO;
    }

    public Piece findPieceAt(Position position, Team turn) {
        Piece piece = board.pickPiece(position);
        if (piece.isEnemy(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
        return piece;
    }

    public void move(Position current, Position next) {
        Piece piece = findPieceAt(current, turn);
        if (!piece.canMove(current, next)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }

        board.movePiece(current, next);
        this.turn = turn.next();
    }

    public Team getTurn() {
        return turn;
    }
}
