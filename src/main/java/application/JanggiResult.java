package application;

import java.util.Map;
import model.JanggiGame;
import model.Team;

public record JanggiResult(
        Team winner,
        boolean bigJangDone,
        Map<Team, Double> finalScore
) {

    public static JanggiResult from(JanggiGame janggiGame) {
        return new JanggiResult(
                janggiGame.resolveWinner(),
                janggiGame.isBigJangDone(),
                janggiGame.calculateFinalScore()
        );
    }
}
