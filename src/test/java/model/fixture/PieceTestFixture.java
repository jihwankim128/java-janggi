package model.fixture;

import java.util.stream.Stream;
import model.coordinate.Position;
import org.junit.jupiter.params.provider.Arguments;

public class PieceTestFixture {

    // 공통
    public static Stream<Arguments> 제자리_이동_케이스() {
        return Stream.of(Arguments.of(new Position(0, 0), new Position(0, 0)));
    }

    // ============================
    // 직선 이동 (車, 包 공통)
    // ============================

    public static Stream<Arguments> 사방위_이동_방향_케이스() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),  // 우 이동
                Arguments.of(new Position(0, 0), new Position(5, 0)),  // 하 이동
                Arguments.of(new Position(5, 5), new Position(0, 5)),  // 상 이동
                Arguments.of(new Position(5, 5), new Position(5, 0))   // 좌 이동
        );
    }

    public static Stream<Arguments> 사간방_대각선_이동_방향_케이스() {
        return Stream.of(
                Arguments.of(new Position(2, 2), new Position(4, 4)),   // 1. 북동 (NE): row 증가, col 증가
                Arguments.of(new Position(5, 5), new Position(3, 7)),   // 2. 남동 (SE): row 감소, col 증가
                Arguments.of(new Position(5, 5), new Position(2, 2)),   // 3. 남서 (SW): row 감소, col 감소
                Arguments.of(new Position(2, 5), new Position(4, 3))    // 4. 북서 (NW): row 증가, col 감소
        );
    }

    // ============================
    // 馬
    // ============================

    public static Stream<Arguments> 마_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),  // 상+좌 (row-2, col-1)
                Arguments.of(new Position(5, 5), new Position(3, 6)),  // 상+우 (row-2, col+1)
                Arguments.of(new Position(5, 5), new Position(7, 4)),  // 하+좌 (row+2, col-1)
                Arguments.of(new Position(5, 5), new Position(7, 6)),  // 하+우 (row+2, col+1)
                Arguments.of(new Position(5, 5), new Position(4, 3)),  // 좌+상 (row-1, col-2)
                Arguments.of(new Position(5, 5), new Position(6, 3)),  // 좌+하 (row+1, col-2)
                Arguments.of(new Position(5, 5), new Position(4, 7)),  // 우+상 (row-1, col+2)
                Arguments.of(new Position(5, 5), new Position(6, 7))   // 우+하 (row+1, col+2)
        );
    }

    public static Stream<Arguments> 마_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(5, 7)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 5)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 7)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(8, 7)),  // 상 이동 (row+3, col+2)
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 象
    // ============================

    public static Stream<Arguments> 상_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(2, 3)),  // 상+좌 (row-3, col-2)
                Arguments.of(new Position(5, 5), new Position(2, 7)),  // 상+우 (row-3, col+2)
                Arguments.of(new Position(5, 5), new Position(8, 3)),  // 하+좌 (row+3, col-2)
                Arguments.of(new Position(5, 5), new Position(8, 7)),  // 하+우 (row+3, col+2)
                Arguments.of(new Position(5, 5), new Position(3, 2)),  // 좌+상 (row-2, col-3)
                Arguments.of(new Position(5, 5), new Position(7, 2)),  // 좌+하 (row+2, col-3)
                Arguments.of(new Position(5, 5), new Position(3, 8)),  // 우+상 (row-2, col+3)
                Arguments.of(new Position(5, 5), new Position(7, 8))   // 우+하 (row+2, col+3)
        );
    }

    public static Stream<Arguments> 상_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),  // 마 이동 (row-2, col-1)
                Arguments.of(new Position(5, 5), new Position(5, 8)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(8, 8)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(4, 4)),  // 1칸 대각선
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 兵 (HAN)
    // ============================

    public static Stream<Arguments> 한나라_병_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(3, 2), new Position(4, 2)),  // 전진 (하)
                Arguments.of(new Position(3, 2), new Position(3, 1)),  // 좌
                Arguments.of(new Position(3, 2), new Position(3, 3))   // 우
        );
    }

    public static Stream<Arguments> 한나라_병_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(3, 2), new Position(2, 2)),  // 후퇴 (상)
                Arguments.of(new Position(3, 2), new Position(5, 2)),  // 두 칸 전진
                Arguments.of(new Position(3, 2), new Position(3, 4)),  // 두 칸 옆
                Arguments.of(new Position(3, 2), new Position(4, 3))   // 대각선
        );
    }

    // ============================
    // 卒 (CHO)
    // ============================

    public static Stream<Arguments> 초나라_졸_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(6, 2), new Position(5, 2)),  // 전진 (상)
                Arguments.of(new Position(6, 2), new Position(6, 1)),  // 좌
                Arguments.of(new Position(6, 2), new Position(6, 3))   // 우
        );
    }

    public static Stream<Arguments> 초나라_졸_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(6, 2), new Position(7, 2)),  // 후퇴 (하)
                Arguments.of(new Position(6, 2), new Position(4, 2)),  // 두 칸 전진
                Arguments.of(new Position(6, 2), new Position(6, 4)),  // 두 칸 옆
                Arguments.of(new Position(6, 2), new Position(5, 3))   // 대각선
        );
    }
}