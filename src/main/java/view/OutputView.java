package view;

import static view.formater.BoardFormatter.COL_NUM;
import static view.formater.BoardFormatter.RED;
import static view.formater.BoardFormatter.RESET;
import static view.formater.BoardFormatter.ROW_NUM;
import static view.formater.BoardFormatter.SPACE;
import static view.formater.BoardFormatter.VERTICAL_LINE;
import static view.formater.BoardFormatter.formatHorizon;
import static view.formater.BoardFormatter.formatSymbol;

import java.util.Map;
import model.Board;
import model.coordinate.Position;
import model.piece.Piece;

public class OutputView {

    private static void displayPositionByPiece(Map<Position, Piece> board) {
        for (int row = 0; row < Board.BOARD_ROW; row++) {
            System.out.print(ROW_NUM[row] + " " + VERTICAL_LINE);
            for (int col = 0; col < Board.BOARD_COL; col++) {
                Piece piece = board.get(new Position(row, col));
                System.out.print(SPACE + formatSymbol(piece));
            }
            System.out.println(SPACE + VERTICAL_LINE);
        }
    }

    private static void displayColIndex() {
        System.out.println();
        System.out.print(SPACE + SPACE + SPACE);
        for (String column : COL_NUM) {
            System.out.print(SPACE + column);
        }
        System.out.println();
    }

    public void displayBoard(Map<Position, Piece> board) {
        displayColIndex();
        String border = formatHorizon(Board.BOARD_COL);
        System.out.println(border);

        displayPositionByPiece(board);

        System.out.println(border);
    }

    public void displayError(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }
}