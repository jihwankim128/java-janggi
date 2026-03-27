package model.piece;

import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#상_이동_가능한_위치")
    void 상은_두칸_직진_후_대각선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece elephant = new Elephant(Team.HAN);
        // when
        boolean canMove = elephant.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#상_이동_불가능한_위치")
    void 상은_이동_규칙에_맞지_않으면_이동할_수_없다(Position current, Position next) {
        // given
        Piece elephant = new Elephant(Team.HAN);
        // when
        boolean canMove = elephant.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }
}