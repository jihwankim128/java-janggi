package repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.JanggiGame;

public class InMemoryJanggiRepository implements JanggiRepository {

    private final Map<Long, JanggiGame> storage = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public Long save(JanggiGame janggiGame) {
        storage.put(++id, janggiGame);
        return id;
    }

    @Override
    public Optional<JanggiGame> findById(Long janggiId) {
        return Optional.ofNullable(storage.get(janggiId));
    }

    @Override
    public void update(Long janggiId, JanggiGame janggiGame) {
        storage.put(janggiId, janggiGame);
    }
}
