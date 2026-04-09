package application;

import java.util.Map;
import model.JanggiGame;
import model.Team;

public record JanggiResultDto(
        Team winner,
        boolean bigJangDone,
        Map<Team, Double> finalScore
) {

    public static JanggiResultDto from(JanggiGame janggiGame) {
        return new JanggiResultDto(
                janggiGame.resolveWinner(),
                janggiGame.isBigJangDone(),
                janggiGame.calculateFinalScore()
        );
    }
}
