package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import model.coordinate.Position;
import model.piece.Piece;
import model.testdouble.FakePiece;
import model.testdouble.SpyBoard;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private Position current = new Position(1, 1);
    private Position next = new Position(2, 1);
    private FakePiece piece = FakePiece.createFake(Team.CHO);

    @Test
    void 장기말을_정상적으로_옮기면_보드에서_이동하고_다음_차례가_된다() {
        // given: (1,1)에 CHO의 이동 가능한 기물이 있고, 경로가 비어있는 상황 시뮬레이션
        SpyBoard board = SpyBoard.withBothGenerals();
        board.arrangePieces(Map.of(current, piece));
        JanggiGame janggiGame = new JanggiGame(board);

        Team prevTurn = janggiGame.getTurn();

        // when
        janggiGame.movePiece(current, next);

        // then
        assertThat(board.pickPiece(next)).isEqualTo(piece);
        assertThat(janggiGame.getTurn()).isEqualTo(prevTurn.opposite());
    }

    @Test
    void 현재_턴의_팀에_맞는_기물을_선택할_수_있다() {
        // given
        SpyBoard board = new SpyBoard(Map.of(current, piece));
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Piece selectedPiece = janggiGame.selectPiece(current);

        // then
        assertThat(selectedPiece).isSameAs(piece);
    }

    @Test
    void 다른_팀의_기물을_선택하면_예외가_발생한다() {
        // given: CHO 턴인데 HAN 기물 배치
        SpyBoard board = new SpyBoard(Map.of(current, FakePiece.createFake(Team.HAN)));
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.selectPiece(current))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 왕이_모두_살아있으면_게임이_진행중이다() {
        // given
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        boolean result = janggiGame.isNotDone();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 한나라_왕만_존재할_때_게임은_종료되고_승리팀은_초나라가_된다() {
        // given
        SpyBoard board = SpyBoard.withOnlyChoGeneral();
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        boolean result = janggiGame.isNotDone();
        Team winner = janggiGame.resolveWinner();

        // then
        assertThat(result).isFalse();
        assertThat(winner).isEqualTo(Team.CHO);
    }

    @Test
    void 초나라_왕만_존재할_때_게임은_종료되고_승리팀은_한나라가_된다() {
        // given
        SpyBoard board = SpyBoard.withOnlyHanGeneral();
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        boolean result = janggiGame.isNotDone();
        Team winner = janggiGame.resolveWinner();

        // then
        assertThat(result).isFalse();
        assertThat(winner).isEqualTo(Team.HAN);
    }

    @Test
    void 게임이_종료되었을_때_기물을_이동하면_예외가_발생한다() {
        // given
        SpyBoard board = SpyBoard.withOnlyHanGeneral();
        board.arrangePieces(Map.of(current, piece));
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.movePiece(current, next))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 게임이_종료되지_않았을_때_승자를_요청하면_예외가_발생한다() {
        // given
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(janggiGame::resolveWinner)
                .isInstanceOf(IllegalArgumentException.class);
    }
}