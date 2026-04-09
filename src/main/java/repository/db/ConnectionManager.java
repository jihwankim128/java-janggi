package repository.db;

import java.sql.Connection;
import java.util.Optional;

public class ConnectionManager {

    private static final ThreadLocal<Connection> THREAD_LOCAL_CONNECTION = new ThreadLocal<>();

    private ConnectionManager() {
    }

    public static void set(Connection connection) {
        THREAD_LOCAL_CONNECTION.set(connection);
    }

    public static Optional<Connection> get() {
        return Optional.ofNullable(THREAD_LOCAL_CONNECTION.get());
    }

    public static boolean isPresent() {
        return get().isPresent();
    }

    public static void remove() {
        THREAD_LOCAL_CONNECTION.remove();
    }
}