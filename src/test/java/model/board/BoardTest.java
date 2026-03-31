package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import model.Team;
import model.piece.Piece;
import model.testdouble.FakePiece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BoardTest {

    private Position source = new Position(5, 0);
    private Position destination = new Position(5, 4);

    @Test
    void 해당_위치에_기물이_있으면_반환한다() {
        // given
        FakePiece piece = FakePiece.이동_가능(Team.CHO);
        Board board = new Board(Map.of(source, piece));

        // when
        Piece pick = board.pickPiece(source);

        // then
        assertThat(pick).isEqualTo(piece);
    }

    @Test
    void 해당_위치에_기물이_없으면_예외가_발생한다() {
        // given
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> board.pickPiece(source))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동_가능한_기물은_정상적으로_이동한다() {
        // given
        FakePiece piece = FakePiece.이동_가능(Team.CHO);
        Board board = new Board(Map.of(source, piece));

        // when
        board.move(source, destination);

        // then
        assertSuccessMoved(board, destination, piece, source);
    }

    @Test
    void 이동_불가능한_기물을_이동하면_예외가_발생한다() {
        // given
        FakePiece piece = FakePiece.이동_불가(Team.CHO);
        Board board = new Board(Map.of(source, piece));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 도착_위치에_아군이_있으면_예외가_발생한다() {
        // given
        FakePiece piece = FakePiece.이동_가능(Team.CHO);
        FakePiece ally = FakePiece.이동_가능(Team.CHO);

        Board board = new Board(Map.of(source, piece, destination, ally));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 도착_위치에_적군이_있으면_이동한다() {
        // given
        FakePiece piece = FakePiece.이동_가능(Team.CHO);
        FakePiece enemy = FakePiece.이동_가능(Team.HAN);

        Board board = new Board(Map.of(source, piece, destination, enemy));

        // when
        board.move(source, destination);

        // then
        assertSuccessMoved(board, destination, piece, source);
    }

    @Test
    void 이동_경로에_기물이_있으면_예외가_발생한다() {
        // given
        Position vertex = new Position(5, 2);
        FakePiece piece = FakePiece.이동_가능하지만_경로_있는_기물(Team.CHO, List.of(vertex));
        FakePiece barricade = FakePiece.이동_가능(Team.HAN);

        Board board = new Board(Map.of(source, piece, vertex, barricade));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_정확히_1개의_기물을_넘어야_한다() {
        // given
        Position vertex = new Position(5, 2);
        FakePiece cannon = FakePiece.이동_가능한_포(Team.CHO, List.of(vertex));
        FakePiece hurdle = FakePiece.이동_가능(Team.HAN);

        Board board = new Board(Map.of(source, cannon, vertex, hurdle));

        // when
        board.move(source, destination);

        // then
        assertSuccessMoved(board, source, cannon, destination);
    }

    @Test
    void 포는_넘을_기물이_없으면_예외가_발생한다() {
        // given
        Position vertex = new Position(5, 2);
        FakePiece cannon = FakePiece.이동_가능한_포(Team.CHO, List.of(vertex));

        Board board = new Board(Map.of(source, cannon));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(Team.class)
    void 포는_포를_넘을_수_없다(Team team) {
        // given
        Position vertex = new Position(5, 2);
        FakePiece cannon = FakePiece.이동_가능한_포(Team.CHO, List.of(vertex));
        FakePiece otherCannon = FakePiece.이동_가능한_포(team, List.of());

        Board board = new Board(Map.of(source, cannon, vertex, otherCannon));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_2개_이상의_기물을_넘으면_예외가_발생한다() {
        // given
        Position firstVertex = new Position(5, 1);
        Position secondVertex = new Position(5, 2);
        FakePiece cannon = FakePiece.이동_가능한_포(Team.CHO, List.of(firstVertex, secondVertex));

        Board board = new Board(Map.of(
                source, cannon,
                firstVertex, FakePiece.이동_가능(Team.HAN),
                secondVertex, FakePiece.이동_가능(Team.HAN)
        ));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void assertSuccessMoved(Board board, Position source, FakePiece piece, Position destination) {
        assertThat(board.pickPiece(source)).isEqualTo(piece);
        assertThat(board.hasPieceAt(destination)).isFalse();
    }
}