package repository;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import model.GameStatus;
import model.JanggiGame;
import model.JanggiState;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;
import repository.mapper.JanggiMapper;

public class JdbcJanggiRepository extends JdbcTemplate implements JanggiRepository {

    public JdbcJanggiRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Long save(JanggiGame janggiGame) {
        String sql = "INSERT INTO janggi_game (status, turn) VALUES (?, ?)";
        Long gameId = executeInsert(sql, janggiGame.status().name(), janggiGame.turn().name());

        savePieces(gameId, janggiGame.getBoard());
        return gameId;
    }

    @Override
    public Optional<JanggiGame> findById(Long janggiId) {
        String gameSql = "SELECT * FROM janggi_game WHERE id = ?";
        return executeQuery(gameSql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Board board = fetchBoard(janggiId);
            JanggiGame janggiGame = new JanggiGame(board);
            return resolveJanggiGame(rs, janggiGame);
        }, janggiId);
    }

    @Override
    public void update(Long janggiId, JanggiGame janggiGame) {
        String updateGameSql = "UPDATE janggi_game SET status = ?, turn = ? WHERE id = ?";
        executeUpdate(updateGameSql, janggiGame.status().name(), janggiGame.turn().name(), janggiId);

        executeUpdate("DELETE FROM piece WHERE game_id = ?", janggiId);
        savePieces(janggiId, janggiGame.getBoard());
    }

    @Override
    public Map<Long, GameStatus> collectGameStatus() {
        String sql = "SELECT id, status FROM janggi_game";
        return executeQuery(sql, rs -> {
            Map<Long, GameStatus> statusMap = new HashMap<>();
            while (rs.next()) {
                statusMap.put(rs.getLong("id"), GameStatus.valueOf(rs.getString("status")));
            }
            return statusMap;
        });
    }

    private void savePieces(Long gameId, Map<Position, Piece> board) {
        String sql = "INSERT INTO piece (game_id, `row`, col, `type`, team) VALUES (?, ?, ?, ?, ?)";
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position pos = entry.getKey();
            Piece piece = entry.getValue();
            executeUpdate(sql, gameId, pos.row(), pos.col(), piece.type().name(), piece.team().name());
        }
    }

    private Board fetchBoard(Long gameId) {
        String pieceSql = "SELECT * FROM piece WHERE game_id = ?";
        return executeQuery(pieceSql, rs -> {
            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Piece piece = JanggiMapper.createPiece(rs.getString("type"), rs.getString("team"));
                Position position = new Position(rs.getInt("row"), rs.getInt("col"));
                pieces.put(position, piece);
            }
            return new Board(pieces);
        }, gameId);
    }

    private Optional<JanggiGame> resolveJanggiGame(ResultSet rs, JanggiGame janggiGame) {
        try {
            JanggiState state = JanggiMapper.createState(rs.getString("status"), rs.getString("turn"));

            Field stateField = JanggiGame.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.set(janggiGame, state);

            return Optional.of(janggiGame);
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] 복원 중 리플렉션 오류 발생", e);
        }
    }
}