package model.movement;

public record Displacement(int rowDiff, int colDiff) {

    public Direction extractCardinal() {
        if (Math.abs(rowDiff) > Math.abs(colDiff)) {
            return Direction.of(rowDiff, 0);
        }
        return Direction.of(0, colDiff);
    }

    public Direction extractDiagonal() {
        return Direction.of(rowDiff, colDiff);
    }
}
