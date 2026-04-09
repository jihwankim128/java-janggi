package ui;

import static model.Team.CHO;
import static model.Team.HAN;
import static ui.Retrier.retry;

import application.JanggiResult;
import application.JanggiService;
import application.MoveCommand;
import model.Team;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;
import ui.view.InputView;
import ui.view.OutputView;

public class NewGameController implements JanggiController {

    private final JanggiService janggiService;

    public NewGameController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    @Override
    public void run() {
        Long janggiId = createJanggiGame();
        OutputView.displayBoard(janggiService.getBoardResponse(janggiId));

        while (janggiService.canPlaying(janggiId)) {
            retry(() -> checkBigJang(janggiId), OutputView::displayError);
            play(janggiId);
        }

        printResult(janggiId);
    }

    private Long createJanggiGame() {
        TeamFormation hanFormation = retry(() -> InputView.readFormationByTeam(HAN), OutputView::displayError);
        TeamFormation choFormation = retry(() -> InputView.readFormationByTeam(CHO), OutputView::displayError);
        return janggiService.createJanggiGame(hanFormation, choFormation);
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
        JanggiResult gameResult = janggiService.getGameResult(janggiId);
        OutputView.displayWinner(gameResult.winner());

        if (gameResult.bigJangDone()) {
            OutputView.displayScore(gameResult.finalScore());
        }
    }
}
