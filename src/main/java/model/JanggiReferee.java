package model;

import java.util.Map;
import model.board.Board;

public class JanggiReferee {

    private static final Team START_TURN = Team.startTurn();
    private static final Team AFTER_TURN = Team.afterTurn();

    private static final double AFTER_TURN_BONUS_SCORE = 1.5;

    private JanggiReferee() {
    }

    public static Map<Team, Double> collectScores(Board board) {
        return Map.of(
                START_TURN, board.calculateBaseScore(START_TURN),
                AFTER_TURN, board.calculateBaseScore(AFTER_TURN) + AFTER_TURN_BONUS_SCORE
        );
    }

    public static Team judgeWinner(Board board) {
        Map<Team, Double> scores = collectScores(board);
        double startTurnScore = scores.get(START_TURN);
        double afterTurnScore = scores.get(AFTER_TURN);

        if (startTurnScore > afterTurnScore) {
            return START_TURN;
        }
        return AFTER_TURN;
    }
}
