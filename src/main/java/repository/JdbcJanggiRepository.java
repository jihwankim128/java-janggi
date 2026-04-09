package repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import model.GameStatus;
import model.JanggiGame;
import model.JanggiState;
import model.Team;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.General;
import model.piece.Guard;
import model.piece.Horse;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Soldier;
import model.state.BigJang;
import model.state.BigJangDone;
import model.state.Finished;
import model.state.Running;

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

    private void savePieces(Long gameId, Map<Position, Piece> board) {
        String sql = "INSERT INTO piece (game_id, `row`, col, `type`, team) VALUES (?, ?, ?, ?, ?)";
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position pos = entry.getKey();
            Piece piece = entry.getValue();
            executeUpdate(sql, gameId, pos.row(), pos.col(), piece.type().name(), piece.team().name());
        }
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
            try {
                Field stateField = JanggiGame.class.getDeclaredField("state");
                stateField.setAccessible(true);

                GameStatus status = GameStatus.valueOf(rs.getString("status"));
                Team turn = Team.valueOf(rs.getString("turn"));
                JanggiState state = createState(status, turn);
                stateField.set(janggiGame, state);

                return Optional.of(janggiGame);
            } catch (Exception e) {
                throw new RuntimeException("[ERROR] 복원 중 리플렉션 오류 발생", e);
            }
        }, janggiId);
    }

    private Board fetchBoard(Long gameId) {
        String pieceSql = "SELECT * FROM piece WHERE game_id = ?";
        return executeQuery(pieceSql, rs -> {
            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Piece piece = createPiece(rs.getString("type"), rs.getString("team"));
                Position position = new Position(rs.getInt("row"), rs.getInt("col"));
                pieces.put(position, piece);
            }
            return new Board(pieces);
        }, gameId);
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

    private JanggiState createState(GameStatus gameStatus, Team turn) {
        return switch (gameStatus) {
            case GameStatus.PLAYING -> new Running(turn);
            case GameStatus.BIG_JANG -> new BigJang(turn);
            case GameStatus.DONE -> new Finished(turn);
            case GameStatus.BIG_JANG_DONE -> new BigJangDone(turn);
        };
    }

    private Piece createPiece(String type, String teamName) {
        Team team = Team.valueOf(teamName);
        return switch (PieceType.valueOf(type)) {
            case CHARIOT -> new Chariot(team);
            case CANNON -> new Cannon(team);
            case HORSE -> new Horse(team);
            case ELEPHANT -> new Elephant(team);
            case SOLDIER -> new Soldier(team);
            case GUARD -> new Guard(team);
            case GENERAL -> new General(team);
        };
    }
}