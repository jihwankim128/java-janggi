package ui;

import static ui.Retrier.retry;

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

    protected JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public abstract void run();

    protected void playGame(Long janggiId) {
        OutputView.displayBoard(janggiService.getBoardResponse(janggiId));

        while (janggiService.canPlaying(janggiId)) {
            retry(() -> checkBigJang(janggiId), OutputView::displayError);
            play(janggiId);
        }

        printResult(janggiId);
    }

    private void play(Long janggiId) {
        if (janggiService.canPlaying(janggiId)) {
            retry(() -> playByTurn(janggiId), OutputView::displayError);
        }
        OutputView.displayBoard(janggiService.getBoardResponse(janggiId));
    }

    private void playByTurn(Long janggiId) {
        Team currentTurn = janggiService.getTurn(janggiId);

        Position current = InputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiService.selectPiece(janggiId, current);

        Position next = InputView.readPiecePositionForArrange(currentTurn, piece);
        janggiService.movePiece(janggiId, new MoveCommand(current, next));
    }

    private void checkBigJang(Long janggiId) {
        if (!janggiService.isBigJang(janggiId)) {
            return;
        }

        boolean bigJang = InputView.readBigJangStatus(janggiService.getTurn(janggiId));
        if (bigJang) {
            janggiService.finishByBigJang(janggiId);
        }
    }

    private void printResult(Long janggiId) {
        JanggiResultDto gameResult = janggiService.getGameResult(janggiId);
        OutputView.displayWinner(gameResult.winner());

        if (gameResult.bigJangDone()) {
            OutputView.displayScore(gameResult.finalScore());
        }
    }
}