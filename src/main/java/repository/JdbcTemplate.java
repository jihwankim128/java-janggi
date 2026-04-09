package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import repository.db.ConnectionManager;

public abstract class JdbcTemplate {

    private final DataSource dataSource;

    protected JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long executeInsert(String sql, Object... parameters) {
        Connection conn = null;
        try {
            conn = getConnection();
            try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParameters(statement, parameters);
                statement.executeUpdate();
                return extractGeneratedKey(statement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public void executeUpdate(String sql, Object... parameters) {
        Connection conn = null;
        try {
            conn = getConnection();
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                setParameters(statement, parameters);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public <T> T executeQuery(String sql, RowMapper<T> mapper, Object... parameters) {
        Connection conn = null;
        try {
            conn = getConnection();
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                setParameters(statement, parameters);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapper.map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private long extractGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("[ERROR] ID를 생성할 수 없습니다.");
        }
    }

    private Connection getConnection() {
        return ConnectionManager.get().orElseGet(() -> {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void closeConnection(Connection conn) {
        if (ConnectionManager.isPresent() || conn == null) {
            return;
        }

        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}