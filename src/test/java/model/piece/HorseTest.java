package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import model.Position;
import model.Team;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HorseTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#마_이동_가능한_위치")
    void 마는_한칸_직진_후_대각선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece horse = new Horse(Team.HAN);
        // when
        boolean canMove = horse.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#마_이동_불가능한_위치")
    void 마는_이동_규칙에_맞지_않으면_이동할_수_없다(Position current, Position next) {
        // given
        Piece horse = new Horse(Team.HAN);
        // when
        boolean canMove = horse.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }
}