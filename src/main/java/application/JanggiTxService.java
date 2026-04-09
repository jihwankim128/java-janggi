package application;

import java.util.Map;
import model.GameStatus;
import model.Team;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;
import repository.db.TransactionTemplate;

public class JanggiTxService implements JanggiService {

    private final TransactionTemplate transactionTemplate;
    private final JanggiService janggiService;

    public JanggiTxService(TransactionTemplate transactionTemplate, JanggiService janggiService) {
        this.transactionTemplate = transactionTemplate;
        this.janggiService = janggiService;
    }

    @Override
    public Long createJanggiGame(TeamFormation hanFormation, TeamFormation choFormation) {
        return transactionTemplate.execute(() -> janggiService.createJanggiGame(hanFormation, choFormation));
    }

    @Override
    public void finishByBigJang(Long janggiId) {
        transactionTemplate.execute(() -> janggiService.finishByBigJang(janggiId));
    }

    @Override
    public void movePiece(Long janggiId, MoveCommand command) {
        transactionTemplate.execute(() -> janggiService.movePiece(janggiId, command));
    }

    @Override
    public JanggiResultDto getGameResult(Long janggiId) {
        return janggiService.getGameResult(janggiId);
    }

    @Override
    public Map<Position, Piece> getBoardResponse(Long janggiId) {
        return janggiService.getBoardResponse(janggiId);
    }

    @Override
    public boolean canPlaying(Long janggiId) {
        return janggiService.canPlaying(janggiId);
    }

    @Override
    public Team getTurn(Long janggiId) {
        return janggiService.getTurn(janggiId);
    }

    @Override
    public Piece selectPiece(Long janggiId, Position current) {
        return janggiService.selectPiece(janggiId, current);
    }

    @Override
    public boolean isBigJang(Long janggiId) {
        return janggiService.isBigJang(janggiId);
    }

    @Override
    public Map<Long, GameStatus> collectGameStatus() {
        return janggiService.collectGameStatus();
    }
}
