package application;

import java.util.Map;
import model.GameStatus;
import model.Team;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;

public interface JanggiService {

    Long createJanggiGame(TeamFormation hanFormation, TeamFormation choFormation);

    void finishByBigJang(Long janggiId);

    void movePiece(Long janggiId, MoveCommand command);

    JanggiResultDto getGameResult(Long janggiId);

    Map<Position, Piece> getBoardResponse(Long janggiId);

    boolean canPlaying(Long janggiId);

    Team getTurn(Long janggiId);

    Piece selectPiece(Long janggiId, Position current);

    boolean isBigJang(Long janggiId);

    Map<Long, GameStatus> collectGameStatus();
}
