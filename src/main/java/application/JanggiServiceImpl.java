package application;

import java.util.Map;
import model.GameStatus;
import model.JanggiGame;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;
import repository.JanggiRepository;

public class JanggiServiceImpl implements JanggiService {
    private final JanggiRepository janggiRepository;

    public JanggiServiceImpl(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public Long createJanggiGame(TeamFormation hanFormation, TeamFormation choFormation) {
        Board board = BoardFactory.generateDefaultPieces();
        board.arrangePieces(hanFormation.generate());
        board.arrangePieces(choFormation.generate());

        JanggiGame janggiGame = new JanggiGame(board);
        return janggiRepository.save(janggiGame);
    }

    public void finishByBigJang(Long janggiId) {
        JanggiGame janggiGame = getGame(janggiId);
        janggiGame.finishByBigJang();
        janggiRepository.update(janggiId, janggiGame);
    }

    public void movePiece(Long janggiId, MoveCommand command) {
        JanggiGame janggiGame = getGame(janggiId);
        janggiGame.movePiece(command.current(), command.next());
        janggiRepository.update(janggiId, janggiGame);
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
