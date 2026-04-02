package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.Team;
import model.board.Position;
import model.testdouble.FakePiece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ElephantTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#상_이동_가능한_위치")
    void 상은_두칸_직진_후_대각선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece elephant = new Elephant(Team.HAN);

        // when
        boolean canMove = elephant.canMove(current, next);

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#상_이동_불가능한_위치")
    void 상은_이동_규칙에_맞지_않으면_이동할_수_없다(Position current, Position next) {
        // given
        Piece elephant = new Elephant(Team.HAN);

        // when
        boolean canMove = elephant.canMove(current, next);

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePathFixture#상_이동_경로_테스트_데이터")
    void 상에_대한_다음_위치까지의_이동_경로를_반환한다(Position current, Position next, List<Position> expectedPath) {
        // given
        Piece chariot = new Elephant(Team.HAN);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 상은_이동_경로에_기물이_있으면_예외가_발생한다() {
        // given
        Piece elephant = new Elephant(Team.HAN);
        List<Piece> obstacles = List.of(FakePiece.createFake(Team.CHO));

        // when & then
        assertThatThrownBy(() -> elephant.validatePathCondition(obstacles))
                .isInstanceOf(IllegalArgumentException.class);
    }
}