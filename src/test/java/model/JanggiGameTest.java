package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.coordinate.Position;
import model.piece.Horse;
import model.piece.Piece;
import model.testdouble.FakePiece;
import model.testdouble.SpyBoard;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 장기말을_정상적으로_옮기면_보드에서_이동하고_다음_차례가_된다() {
        // given: (1,1)에 CHO의 이동 가능한 기물이 있고, 경로가 비어있는 상황 시뮬레이션
        Position source = new Position(1, 1);
        Position destination = new Position(2, 2);
        FakePiece piece = FakePiece.createFake(Team.CHO);

        SpyBoard board = new SpyBoard(piece);
        JanggiGame janggiGame = new JanggiGame(board);
        Team prevTurn = janggiGame.getTurn();

        // when
        janggiGame.movePiece(source, destination);

        // then
        assertThat(board.pickPiece(destination)).isEqualTo(piece);
        assertThat(janggiGame.getTurn()).isEqualTo(prevTurn.next());
    }

    @Test
    void 현재_턴의_팀에_맞는_기물을_선택할_수_있다() {
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
    void 다른_팀의_기물을_선택하면_예외가_발생한다() {
        // given: CHO 턴인데 HAN 기물 배치
        SpyBoard board = new SpyBoard(new Horse(Team.HAN));
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.selectPiece(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}