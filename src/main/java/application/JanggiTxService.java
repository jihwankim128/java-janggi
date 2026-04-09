package application;

import model.board.TeamFormation;
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
}
