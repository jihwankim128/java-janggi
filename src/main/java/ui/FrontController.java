package ui;

import static ui.Retrier.retry;

import application.JanggiService;
import java.util.Map;
import java.util.Optional;
import repository.InMemoryJanggiRepository;
import ui.view.InputView;
import ui.view.OutputView;

public class FrontController {

    private final Map<GameMenu, JanggiController> controllers;

    public FrontController() {
        JanggiService janggiService = new JanggiService(new InMemoryJanggiRepository());

        controllers = Map.of(
                GameMenu.CONTINUE, new ContinueController(janggiService),
                GameMenu.NEW_GAME, new NewGameController(janggiService)
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
