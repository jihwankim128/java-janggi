package ui;

import static ui.Retrier.retry;

import java.util.Map;
import java.util.Optional;
import ui.view.InputView;
import ui.view.OutputView;

public class FrontController {

    private final Map<GameMenu, JanggiController> controllers;

    public FrontController() {
        controllers = Map.of(
                GameMenu.CONTINUE, new ContinueController(),
                GameMenu.NEW_GAME, new NewGameController()
        );
    }

    public void run() {
        GameMenu gameMenu;
        do {
            gameMenu = retry(InputView::readGameMenu, OutputView::displayError);
            Optional.ofNullable(controllers.get(gameMenu))
                    .ifPresent(JanggiController::run);
        } while (gameMenu != GameMenu.END);
    }
}
