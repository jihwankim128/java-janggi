package ui;

import static ui.Retrier.retry;

import application.JanggiQueryService;
import application.JanggiService;
import java.util.Map;
import model.GameStatus;
import ui.view.InputView;
import ui.view.OutputView;

public class ContinueController extends JanggiController {

    protected ContinueController(JanggiService janggiService, JanggiQueryService janggiQueryService) {
        super(janggiService, janggiQueryService);
    }

    @Override
    public void run() {
        Map<Long, GameStatus> gameStatusById = janggiQueryService.collectGameStatus();
        if (gameStatusById.isEmpty()) {
            OutputView.displayNoGame();
            return;
        }

        process(gameStatusById);
    }

    private void process(Map<Long, GameStatus> gameStatusById) {
        Long gameId = retry(() -> InputView.readPlayGameId(gameStatusById), OutputView::displayError);

        try {
            playGame(gameId);
        } catch (IllegalArgumentException e) {
            OutputView.displayError(e.getMessage());
        }
    }
}
