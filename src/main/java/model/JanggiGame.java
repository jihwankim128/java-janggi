package model;

import java.util.List;
import java.util.Map;
import model.board.Board;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;
import model.piece.Piece;

public class JanggiGame {

    private static final double AFTER_TURN_BONUS_SCORE = 1.5;
    private static final Team START_TURN = Team.CHO;

    private final Board board;
    private Team turn;
    private GameStatus status;

    public JanggiGame(Board board) {
        this.board = board;
        this.turn = START_TURN;
        this.status = GameStatus.PLAYING;
    }

    public void movePiece(Position current, Position next) {
        Piece piece = selectPiece(current);

        List<Position> path = piece.pathTo(current, next);
        List<Piece> pieces = board.extractPiecesByPath(path);
        piece.validatePathCondition(pieces);

        board.move(current, next);
        changeGameState();
    }

    public Piece selectPiece(Position position) {
        validateGamePlay();
        Piece piece = board.pickPiece(position);
        validateAlly(piece);
        return piece;
    }

    public Team resolveWinner() {
        validateGameDone();
        if (isBigJangDone()) {
            return resolveBigJangWinner();
        }
        return resolveNormalWinner();
    }

    public Map<Team, Double> calculateFinalScore() {
        Team afterTurn = START_TURN.opposite();
        return Map.of(
                START_TURN, board.calculateBaseScore(START_TURN),
                afterTurn, board.calculateBaseScore(afterTurn) + AFTER_TURN_BONUS_SCORE
        );
    }

    public boolean canPlaying() {
        return this.status != GameStatus.DONE && !isBigJangDone();
    }

    public void finishByBigJang() {
        if (!isBigJang()) {
            throw new IllegalStateException("현재 빅장 상태가 아닙니다.");
        }
        this.status = GameStatus.BIG_JANG_DONE;
    }

    public boolean isBigJang() {
        return this.status == GameStatus.BIG_JANG;
    }

    public boolean isBigJangDone() {
        return this.status == GameStatus.BIG_JANG_DONE;
    }

    private boolean checkBigJang() {
        Position allyGeneralPosition = board.findGeneralPositionByTeam(turn);
        Position enemyGeneralPosition = board.findGeneralPositionByTeam(turn.opposite());
        return determineBigJang(enemyGeneralPosition, allyGeneralPosition);
    }

    private boolean determineBigJang(Position enemyGeneralPosition, Position allyGeneralPosition) {
        Displacement displacement = enemyGeneralPosition.toDisplacement(allyGeneralPosition);
        if (displacement.isNotStraight()) {
            return false;
        }

        Direction direction = displacement.extractCardinal();
        List<Position> path = direction.pathTo(allyGeneralPosition, enemyGeneralPosition);
        List<Piece> pieces = board.extractPiecesByPath(path);
        return pieces.isEmpty();
    }

    private void changeGameState() {
        this.turn = turn.opposite();
        if (!board.isAliveGeneral(turn)) {
            this.status = GameStatus.DONE;
            return;
        }
        if (checkBigJang()) {
            this.status = GameStatus.BIG_JANG;
            return;
        }
        this.status = GameStatus.PLAYING;
    }

    private Team resolveNormalWinner() {
        if (board.isAliveGeneral(START_TURN)) {
            return START_TURN;
        }
        return START_TURN.opposite();
    }

    private Team resolveBigJangWinner() {
        Map<Team, Double> scores = calculateFinalScore();
        Team afterTurn = START_TURN.opposite();
        if (scores.get(START_TURN) > scores.get(afterTurn)) {
            return START_TURN;
        }
        return afterTurn;
    }

    private void validateGamePlay() {
        if (!canPlaying()) {
            throw new IllegalStateException("게임이 종료되었으므로 장기 게임을 진행할 수 없습니다.");
        }
    }

    private void validateAlly(Piece piece) {
        if (!piece.isSameTeam(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
    }

    private void validateGameDone() {
        if (canPlaying()) {
            throw new IllegalStateException("아직 게임이 진행중입니다.");
        }
    }

    public Team turn() {
        return turn;
    }

    public Map<Position, Piece> getBoard() {
        return board.board();
    }
}
