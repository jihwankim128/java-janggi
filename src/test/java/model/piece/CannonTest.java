package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#사방위_이동_방향_케이스")
    void 포는_직선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);

        // when
        boolean canMove = cannon.canMove(current, next);

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePositionFixture#사간방_대각선_이동_방향_케이스")
    @MethodSource("model.fixture.PieceMovePositionFixture#제자리_이동_케이스")
    void 포는_대각선이나_제자리로_이동할_수_없다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);

        // when
        boolean canMove = cannon.canMove(current, next);

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceMovePathFixture#사방위_이동_경로_테스트_데이터")
    void 포에_대한_다음_위치까지의_이동_경로를_반환한다(Position current, Position next, List<Position> expectedPath) {
        // given
        Piece chariot = new Cannon(Team.HAN);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }
}