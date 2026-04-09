package repository;

import java.util.Optional;
import model.JanggiGame;

public interface JanggiRepository {

    Long save(JanggiGame janggiGame);

    Optional<JanggiGame> findById(Long janggiId);

    void update(Long janggiId, JanggiGame janggiGame);
}
