package ui;

import static ui.Retrier.retry;

import application.JanggiService;
import java.util.Map;
import model.GameStatus;
import ui.view.InputView;
import ui.view.OutputView;

public class ContinueController extends JanggiController {

    protected ContinueController(JanggiService janggiService) {
        super(janggiService);
    }

    @Override
    public void run() {
        Map<Long, GameStatus> gameStatusById = janggiService.collectGameStatus();
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
