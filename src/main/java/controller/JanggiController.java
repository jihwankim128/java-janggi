package controller;

import static controller.Retrier.retry;
import static model.Team.CHO;
import static model.Team.HAN;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import model.Board;
import model.BoardFactory;
import model.Position;
import model.formation.FormationFactory;
import model.formation.JanggiFormation;
import model.piece.Piece;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = retry(() -> inputView.readFormationNumber(HAN, formations), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationNumber(CHO, formations), processError());

        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        outputView.displayBoard(board.getBoard());
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
