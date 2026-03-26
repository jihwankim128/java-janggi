package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import model.coordinate.Position;
import model.Team;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_가능한_위치")
    void 포는_직선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);
        // when
        boolean canMove = cannon.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_불가능한_위치")
    void 포는_대각선으로_이동할_수_없다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);
        // when
        boolean canMove = cannon.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }
}