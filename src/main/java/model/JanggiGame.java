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
        validateGamePlay();
        Piece piece = selectPiece(current);

        List<Position> path = piece.pathTo(current, next);
        List<Piece> pieces = board.extractPiecesByPath(path);
        piece.validatePathCondition(pieces);

        board.move(current, next);
        this.turn = turn.opposite();
    }

    public boolean isNotDone() {
        return board.isAliveGeneral(Team.HAN) && board.isAliveGeneral(Team.CHO);
    }

    public Team resolveWinner() {
        validateGameDone();
        if (board.isAliveGeneral(Team.HAN)) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public Piece selectPiece(Position position) {
        Piece piece = board.pickPiece(position);
        validateAlly(piece);
        return piece;
    }

    private boolean isDone() {
        return !isNotDone();
    }

    private void validateGamePlay() {
        if (isDone()) {
            throw new IllegalStateException("게임이 종료되었으므로 이동할 수 없습니다.");
        }
    }

    private void validateAlly(Piece piece) {
        if (!piece.isSameTeam(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
    }

    private void validateGameDone() {
        if (isNotDone()) {
            throw new IllegalArgumentException("아직 게임이 진행중입니다.");
        }
    }

    public Team getTurn() {
        return turn;
    }
}
