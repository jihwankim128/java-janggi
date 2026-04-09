package application;

import model.board.TeamFormation;

public interface JanggiService {

    Long createJanggiGame(TeamFormation hanFormation, TeamFormation choFormation);

    void finishByBigJang(Long janggiId);

    void movePiece(Long janggiId, MoveCommand command);

}
