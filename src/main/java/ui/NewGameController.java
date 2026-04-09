package ui;

import static model.Team.CHO;
import static model.Team.HAN;
import static ui.Retrier.retry;

import application.JanggiQueryService;
import application.JanggiService;
import model.board.TeamFormation;
import ui.view.InputView;
import ui.view.OutputView;

public class NewGameController extends JanggiController {

    protected NewGameController(JanggiService janggiService, JanggiQueryService janggiQueryService) {
        super(janggiService, janggiQueryService);
    }

    @Override
    public void run() {
        TeamFormation hanFormation = retry(() -> InputView.readFormationByTeam(HAN), OutputView::displayError);
        TeamFormation choFormation = retry(() -> InputView.readFormationByTeam(CHO), OutputView::displayError);
        Long janggiId = janggiService.createJanggiGame(hanFormation, choFormation);
        playGame(janggiId);
    }
}
