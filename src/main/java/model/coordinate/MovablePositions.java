package model.coordinate;

import java.util.Iterator;
import java.util.List;

public class MovablePositions implements Iterable<Position> {
    private final List<Position> paths ;

    private static final MovablePositions EMPTY = new MovablePositions(List.of());

    public MovablePositions(List<Position> paths) {
        this.paths = List.copyOf(paths);
    }

    public static MovablePositions empty() {
        return EMPTY;
    }

    @Override
    public Iterator<Position> iterator() {
        return paths.iterator();
    }
}
