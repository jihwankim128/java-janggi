package ui;

import static ui.Retrier.retry;

import application.JanggiQueryService;
import application.JanggiResultDto;
import application.JanggiService;
import application.MoveCommand;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;
import ui.view.InputView;
import ui.view.OutputView;

public abstract class JanggiController {

    protected final JanggiService janggiService;
    protected final JanggiQueryService janggiQueryService;

    protected JanggiController(JanggiService janggiService, JanggiQueryService janggiQueryService) {
        this.janggiService = janggiService;
        this.janggiQueryService = janggiQueryService;
    }

    public abstract void run();

    protected void playGame(Long janggiId) {
        OutputView.displayBoard(janggiQueryService.getBoardResponse(janggiId));

        while (janggiQueryService.canPlaying(janggiId)) {
            retry(() -> checkBigJang(janggiId), OutputView::displayError);
            play(janggiId);
        }

        printResult(janggiId);
    }

    private void play(Long janggiId) {
        if (janggiQueryService.canPlaying(janggiId)) {
            retry(() -> playByTurn(janggiId), OutputView::displayError);
        }
        OutputView.displayBoard(janggiQueryService.getBoardResponse(janggiId));
    }

    private void playByTurn(Long janggiId) {
        Team currentTurn = janggiQueryService.getTurn(janggiId);

        Position current = InputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiQueryService.selectPiece(janggiId, current);

        Position next = InputView.readPiecePositionForArrange(currentTurn, piece);
        janggiService.movePiece(janggiId, new MoveCommand(current, next));
    }

    private void checkBigJang(Long janggiId) {
        if (!janggiQueryService.isBigJang(janggiId)) {
            return;
        }

        boolean bigJang = InputView.readBigJangStatus(janggiQueryService.getTurn(janggiId));
        if (bigJang) {
            janggiService.finishByBigJang(janggiId);
        }
    }

    private void printResult(Long janggiId) {
        JanggiResultDto gameResult = janggiQueryService.getGameResult(janggiId);
        OutputView.displayWinner(gameResult.winner());

        if (gameResult.bigJangDone()) {
            OutputView.displayScore(gameResult.finalScore());
        }
    }
}