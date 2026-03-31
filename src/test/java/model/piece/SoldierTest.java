package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierTest {

    @ParameterizedTest(name = "{0}나라 졸 일 때, {1}에서 {2}로 이동할 수 있다.")
    @MethodSource("model.fixture.PieceMovePositionFixture#졸_병_이동_가능한_위치")
    void 졸_병_이동_성공_테스트(Team team, Position current, Position next) {
        // given
        Piece soldier = new Soldier(team);

        // when & then
        assertThat(soldier.canMove(current, next)).isTrue();
    }

    @ParameterizedTest(name = "{0}나라 졸 일 때, {1}에서 {2}로 이동할 수 없다.")
    @MethodSource("model.fixture.PieceMovePositionFixture#졸_병_이동_불가능한_위치")
    void 졸_병_이동_실패_테스트(Team team, Position current, Position next) {
        // given
        Piece soldier = new Soldier(team);

        // when & then
        assertThat(soldier.canMove(current, next)).isFalse();
    }

    @ParameterizedTest(name = "{0}나라 졸 일 때, {1}에서 {2}로 이동 시 무조건 경로가 비어있다.")
    @MethodSource("model.fixture.PieceMovePathFixture#졸_병_이동_경로_테스트_데이터")
    void 졸_병_경로_테스트(Team team, Position current, Position next) {
        // given
        Piece soldier = new Soldier(team);

        // when
        List<Position> path = soldier.extractPath(current, next);

        // then
        assertThat(path).isEmpty();
    }
}