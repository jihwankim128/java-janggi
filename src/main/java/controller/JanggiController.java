package controller;

import model.JanggiFormation;
import model.Team;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = Retrier.retry(() ->
                inputView.readFormationNumber(Team.HAN, formations), processError());
        JanggiFormation choFormation = Retrier.retry(() ->
                inputView.readFormationNumber(Team.CHO, formations), processError());
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
