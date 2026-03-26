package model.coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    EAST(0, 1),
    WEST(0, -1),
    NORTH(-1, 0),
    SOUTH(1, 0),
    NORTH_EAST(-1, 1),
    SOUTH_EAST(1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_WEST(1, -1);

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Direction from(Position start, Position end) {
        Displacement displacement = end.minus(start);
        return Stream.of(values())
                .filter(direction -> hasSameDirection(direction, displacement))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("직선 방향이 아닙니다."));
    }

    /**
     * 변위를 직선 1칸 + 대각선 N칸으로 분해한다.
     * 마: [직선, 대각선], 상: [직선, 대각선, 대각선]
     */
    public static List<Direction> decomposeToCardinalAndDiagonal(Position start, Position end) {
        Displacement displacement = end.minus(start);
        Direction cardinal = resolveCardinal(displacement);
        Direction diagonal = resolveDiagonal(displacement);
        int diagonalCount = Math.min(Math.abs(displacement.row()), Math.abs(displacement.col()));

        List<Direction> directions = new ArrayList<>();
        directions.add(cardinal);
        for (int i = 0; i < diagonalCount; i++) {
            directions.add(diagonal);
        }
        return directions;
    }

    private static Direction resolveCardinal(Displacement displacement) {
        if (Math.abs(displacement.row()) > Math.abs(displacement.col())) {
            return findByRowCol(Integer.signum(displacement.row()), 0);
        }
        return findByRowCol(0, Integer.signum(displacement.col()));
    }

    private static Direction resolveDiagonal(Displacement displacement) {
        return findByRowCol(Integer.signum(displacement.row()), Integer.signum(displacement.col()));
    }

    private static Direction findByRowCol(int row, int col) {
        return Stream.of(values())
                .filter(d -> d.row == row && d.col == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 방향입니다."));
    }

    private static boolean hasSameDirection(Direction direction, Displacement displacement) {
        /*
        두 방향 벡터 (a,b), (c,d)가 실수배이려면 비례 관계 a:c = b:d 가 성립해야 한다.
        나눗셈의 0 처리 문제를 피하기 위해 ad = bc 형태로 변환하여 판별하고,
        부호 곱 >= 0 조건으로 반대 방향을 걸러낸다.
         */
        return direction.row * displacement.col() == direction.col * displacement.row()
                && direction.row * displacement.row() >= 0
                && direction.col * displacement.col() >= 0;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }
}
