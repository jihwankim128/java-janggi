package controller;

import static controller.Retrier.retry;
import static model.Team.CHO;
import static model.Team.HAN;

import java.util.Map;
import java.util.function.Consumer;
import model.JanggiGame;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.board.TeamFormation;
import model.coordinate.Position;
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
        JanggiGame janggiGame = createJanggiGame();
        outputView.displayBoard(janggiGame.getBoard());

        while (janggiGame.canPlaying()) {
            retry(() -> checkBigJang(janggiGame), processError());
            play(janggiGame);
        }

        printResult(janggiGame);
    }

    private JanggiGame createJanggiGame() {
        TeamFormation hanFormation = retry(() -> inputView.readFormationByTeam(HAN), processError());
        TeamFormation choFormation = retry(() -> inputView.readFormationByTeam(CHO), processError());

        Board board = BoardFactory.generateDefaultPieces();
        board.arrangePieces(hanFormation.generate());
        board.arrangePieces(choFormation.generate());
        return new JanggiGame(board);
    }

    private void play(JanggiGame janggiGame) {
        if (janggiGame.canPlaying()) {
            retry(() -> playByTurn(janggiGame), processError());
        }
        outputView.displayBoard(janggiGame.getBoard());
    }

    private void playByTurn(JanggiGame janggiGame) {
        Team currentTurn = janggiGame.turn();

        Position current = inputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiGame.selectPiece(current);

        Position next = inputView.readPiecePositionForArrange(currentTurn, piece);
        janggiGame.movePiece(current, next);
    }

    private void checkBigJang(JanggiGame janggiGame) {
        if (!janggiGame.isBigJang()) {
            return;
        }

        boolean bigJang = inputView.readBigJangStatus(janggiGame.turn());
        if (bigJang) {
            janggiGame.finishByBigJang();
        }
    }

    private void printResult(JanggiGame janggiGame) {
        Team winner = janggiGame.resolveWinner();
        outputView.displayWinner(winner);

        if (janggiGame.isBigJangDone()) {
            Map<Team, Double> finalScore = janggiGame.calculateFinalScore();
            outputView.displayScore(finalScore);
        }
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
