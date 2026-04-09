package repository;

import java.util.HashMap;
import java.util.List;
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
        String sql = "SELECT * FROM janggi_game WHERE id = ?";
        return executeQuery(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Board board = fetchBoard(janggiId);
            JanggiState state = JanggiMapper.createState(rs.getString("status"), rs.getString("turn"));
            return Optional.of(new JanggiGame(board, state));
        }, janggiId);
    }

    @Override
    public void update(Long janggiId, JanggiGame janggiGame) {
        String sql = "UPDATE janggi_game SET status = ?, turn = ? WHERE id = ?";
        executeUpdate(sql, janggiGame.status().name(), janggiGame.turn().name(), janggiId);

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

        List<Object[]> parameters = board.entrySet().stream()
                .map(entry -> {
                    Position pos = entry.getKey();
                    Piece piece = entry.getValue();
                    return new Object[]{gameId, pos.row(), pos.col(), piece.type().name(), piece.team().name()};
                })
                .toList();

        executeBatch(sql, parameters);
    }

    private Board fetchBoard(Long gameId) {
        String sql = "SELECT * FROM piece WHERE game_id = ?";
        return executeQuery(sql, rs -> {
            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Piece piece = JanggiMapper.createPiece(rs.getString("type"), rs.getString("team"));
                Position position = new Position(rs.getInt("row"), rs.getInt("col"));
                pieces.put(position, piece);
            }
            return new Board(pieces);
        }, gameId);
    }
}