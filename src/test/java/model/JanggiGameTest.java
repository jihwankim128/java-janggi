package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.coordinate.Position;
import model.piece.Horse;
import model.piece.Piece;
import model.testdouble.SpyBoard;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 장기에서_장기말을_옮기면_보드에서_장기말이_이동하고_다음_차례가_된다() {
        // given
        SpyBoard board = SpyBoard.cho();
        JanggiGame janggiGame = new JanggiGame(board);
        Team prevTurn = janggiGame.getTurn();

        // when
        janggiGame.movePiece(new Position(1, 1), new Position(2, 2));

        // then
        assertThat(board.isMoved).isTrue();
        assertThat(janggiGame.getTurn()).isEqualTo(prevTurn.next());
    }

    @Test
    void 장기에서_장기말을_옮길_때_다른_팀의_장기말을_옮기면_예외가_발생한다() {
        // given
        SpyBoard board = SpyBoard.han();
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.movePiece(new Position(1, 1), new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장기에서_현재_턴에_대한_장기물을_선택할_수_있다() {
        // given
        Piece piece = new Horse(Team.CHO);
        SpyBoard board = new SpyBoard(piece);
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Piece selectedPiece = janggiGame.selectPiece(new Position(1, 1));

        // then
        assertThat(selectedPiece).isSameAs(piece);
    }

    @Test
    void 장기에서_다른_턴에_대한_장기물을_선택하면_예외가_발생한다() {
        // given
        SpyBoard board = new SpyBoard(new Horse(Team.HAN));
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.selectPiece(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}