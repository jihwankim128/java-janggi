package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.piece.Piece;
import model.testdouble.FakePiece;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void 한나라_다음_차례는_초나라다() {
        // given
        Team han = Team.HAN;

        // when
        Team next = han.next();

        // then
        assertThat(next).isEqualTo(Team.CHO);
    }

    @Test
    void 초나라_다음_차례는_한나라다() {
        // given
        Team cho = Team.CHO;

        // when
        Team next = cho.next();

        // then
        assertThat(next).isEqualTo(Team.HAN);
    }

    @Test
    void 자신의_팀이_아닌_기물을_검증하면_예외가_발생한다() {
        // given
        Team cho = Team.CHO;
        Piece hanPiece = FakePiece.createFake(Team.HAN);

        // when & then
        assertThatThrownBy(() -> cho.validateAlly(hanPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }
}