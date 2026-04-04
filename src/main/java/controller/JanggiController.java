package controller;

import static controller.Retrier.retry;
import static model.Team.CHO;
import static model.Team.HAN;

import java.util.function.Consumer;
import model.JanggiGame;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.board.JanggiFormation;
import model.board.Position;
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
        Board board = createBoardByFormation();
        outputView.displayBoard(board.board());

        JanggiGame janggiGame = new JanggiGame(board);
        while (true) {
            retry(() -> playByTurn(janggiGame), processError());
            outputView.displayBoard(board.board());
        }
    }

    private Board createBoardByFormation() {
        JanggiFormation hanFormation = retry(() -> inputView.readFormationByTeam(HAN), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationByTeam(CHO), processError());

        Board board = BoardFactory.generateDefaultPieces();
        board.arrangePieces(hanFormation.generateByTeam(HAN));
        board.arrangePieces(choFormation.generateByTeam(CHO));
        return board;
    }

    private void playByTurn(JanggiGame janggiGame) {
        Team currentTurn = janggiGame.getTurn();

        Position current = inputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiGame.selectPiece(current);

        Position next = inputView.readPiecePositionForArrange(currentTurn, piece);
        janggiGame.movePiece(current, next);
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
