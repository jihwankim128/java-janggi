package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import model.coordinate.Position;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.Piece;
import model.piece.Soldier;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @Test
    void 포가_1개의_기물을_뛰어넘어_이동한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(1, 0));

        // then
        assertThat(board.pickPiece(new Position(1, 0)).isCannon()).isTrue();
    }

    @Test
    void 포가_가로로_1개의_기물을_뛰어넘어_이동한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(5, 3), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(5, 6));

        // then
        assertThat(board.pickPiece(new Position(5, 6)).isCannon()).isTrue();
    }

    @Test
    void 포가_경로에_기물이_없으면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_경로에_기물이_2개_이상이면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(4, 0), new Soldier(Team.HAN));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_경로에_포를_뛰어넘으면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Cannon(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_적군_기물을_잡을_수_있다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        pieces.put(new Position(1, 0), new Chariot(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(1, 0));

        // then
        assertThat(board.pickPiece(new Position(1, 0)).isCannon()).isTrue();
    }
}