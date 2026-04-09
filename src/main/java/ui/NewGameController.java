package ui;

import static model.Team.CHO;
import static model.Team.HAN;
import static ui.Retrier.retry;

import java.util.Map;
import model.JanggiGame;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;
import ui.view.InputView;
import ui.view.OutputView;

public class NewGameController implements JanggiController {

    @Override
    public void run() {
        JanggiGame janggiGame = createJanggiGame();
        OutputView.displayBoard(janggiGame.getBoard());

        while (janggiGame.canPlaying()) {
            retry(() -> checkBigJang(janggiGame), OutputView::displayError);
            play(janggiGame);
        }

        printResult(janggiGame);
    }

    private JanggiGame createJanggiGame() {
        TeamFormation hanFormation = retry(() -> InputView.readFormationByTeam(HAN), OutputView::displayError);
        TeamFormation choFormation = retry(() -> InputView.readFormationByTeam(CHO), OutputView::displayError);

        Board board = BoardFactory.generateDefaultPieces();
        board.arrangePieces(hanFormation.generate());
        board.arrangePieces(choFormation.generate());
        return new JanggiGame(board);
    }

    private void play(JanggiGame janggiGame) {
        if (janggiGame.canPlaying()) {
            retry(() -> playByTurn(janggiGame), OutputView::displayError);
        }
        OutputView.displayBoard(janggiGame.getBoard());
    }

    private void playByTurn(JanggiGame janggiGame) {
        Team currentTurn = janggiGame.turn();

        Position current = InputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiGame.selectPiece(current);

        Position next = InputView.readPiecePositionForArrange(currentTurn, piece);
        janggiGame.movePiece(current, next);
    }

    private void checkBigJang(JanggiGame janggiGame) {
        if (!janggiGame.isBigJang()) {
            return;
        }

        boolean bigJang = InputView.readBigJangStatus(janggiGame.turn());
        if (bigJang) {
            janggiGame.finishByBigJang();
        }
    }

    private void printResult(JanggiGame janggiGame) {
        Team winner = janggiGame.resolveWinner();
        OutputView.displayWinner(winner);

        if (janggiGame.isBigJangDone()) {
            Map<Team, Double> finalScore = janggiGame.calculateFinalScore();
            OutputView.displayScore(finalScore);
        }
    }
}
