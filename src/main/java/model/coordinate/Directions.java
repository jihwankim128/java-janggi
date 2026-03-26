package model.coordinate;

import java.util.Iterator;
import java.util.Set;

public class Directions implements Iterable<Direction> {
    public static final Directions CARDINAL_DIRECTIONS = new Directions(Set.of(
            Direction.EAST, Direction.WEST,
            Direction.SOUTH, Direction.NORTH));
    public static final Directions DIAGONAL_DIRECTIONS = new Directions(Set.of(
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    ));
    private final Set<Direction> directions;

    public Directions(Set<Direction> directions) {
        this.directions = Set.copyOf(directions);
    }

    @Override
    public Iterator<Direction> iterator() {
        return directions.iterator();
    }
}

