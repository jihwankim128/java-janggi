package model;

import java.util.List;
import model.board.Board;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;
import model.piece.Piece;
import model.state.BigJang;
import model.state.Finished;
import model.state.Running;

public abstract class JanggiState {
    private final Team turn;

    protected JanggiState(Team turn) {
        this.turn = turn;
    }

    public void validateGamePlay() {
        if (!canPlaying()) {
            throw new IllegalStateException("게임이 종료되었으므로 장기 게임을 진행할 수 없습니다.");
        }
    }

    public JanggiState next(Board board) {
        Team nextTurn = this.turn.opposite();

        if (!board.isAliveGeneral(nextTurn)) {
            return new Finished(this.turn);
        }
        if (checkBigJang(board, nextTurn)) {
            return new BigJang(nextTurn);
        }
        return new Running(nextTurn);
    }

    private boolean checkBigJang(Board board, Team nextTurn) {
        Position allyGeneralPosition = board.findGeneralPositionByTeam(nextTurn);
        Position enemyGeneralPosition = board.findGeneralPositionByTeam(nextTurn.opposite());
        Displacement displacement = enemyGeneralPosition.toDisplacement(allyGeneralPosition);
        if (displacement.isNotStraight()) {
            return false;
        }

        Direction direction = displacement.extractCardinal();
        List<Position> path = direction.pathTo(allyGeneralPosition, enemyGeneralPosition);
        List<Piece> pieces = board.extractPiecesByPath(path);
        return pieces.isEmpty();
    }

    public abstract Team resolveWinner(Board board);

    public abstract boolean canPlaying();

    public abstract GameStatus status();

    public Team turn() {
        return turn;
    }
}