package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#사방위_이동_방향_케이스")
    void 포는_직선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);

        // when
        boolean canMove = cannon.canMove(current, next);

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#사간방_대각선_이동_방향_케이스")
    @MethodSource("model.fixture.PieceTestFixture#제자리_이동_케이스")
    void 포는_대각선이나_제자리로_이동할_수_없다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);

        // when
        boolean canMove = cannon.canMove(current, next);

        // then
        assertThat(canMove).isFalse();
    }
}