package application;

import java.util.Map;
import model.GameStatus;
import model.JanggiGame;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;
import repository.JanggiRepository;

public class JanggiQueryService {

    private final JanggiRepository janggiRepository;

    public JanggiQueryService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public JanggiResultDto getGameResult(Long janggiId) {
        JanggiGame janggiGame = getGame(janggiId);
        return JanggiResultDto.from(janggiGame);
    }

    public Map<Position, Piece> getBoardResponse(Long janggiId) {
        return getGame(janggiId).getBoard();
    }

    public boolean canPlaying(Long janggiId) {
        return getGame(janggiId).canPlaying();
    }

    public Team getTurn(Long janggiId) {
        return getGame(janggiId).turn();
    }

    public Piece selectPiece(Long janggiId, Position current) {
        return getGame(janggiId).selectPiece(current);
    }

    public boolean isBigJang(Long janggiId) {
        return getGame(janggiId).isBigJang();
    }

    public Map<Long, GameStatus> collectGameStatus() {
        return janggiRepository.collectGameStatus();
    }

    private JanggiGame getGame(Long janggiId) {
        return janggiRepository.findById(janggiId)
                .orElseThrow(() -> new IllegalArgumentException(janggiId + "번 장기 게임이 존재하지 않습니다."));
    }
}
