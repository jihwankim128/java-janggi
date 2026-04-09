package application;

import model.JanggiGame;
import model.board.Board;
import model.board.BoardFactory;
import model.board.TeamFormation;
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

    private JanggiGame getGame(Long janggiId) {
        return janggiRepository.findById(janggiId)
                .orElseThrow(() -> new IllegalArgumentException(janggiId + "번 장기 게임이 존재하지 않습니다."));
    }
}
