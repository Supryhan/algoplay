package problemslab.lx;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UserCacheImpl implements UserCache {
  private Map<Long, UserEntity> cache = new HashMap<>();
  private Map<Long, Instant> timestamps = new HashMap<>();
  private Instant lastUpdateTime = Instant.now();
  private long savePeriod = 3600;

  @Override
  public boolean containsUser(Long id) {
    evictExpiredEntries();
    return cache.containsKey(id);
  }

  @Override
  public UserEntity getUser(Long id) {
    evictExpiredEntries();
    return cache.get(id);
  }

  @Override
  public void evictExpiredEntries() {
    Instant now = Instant.now();
    if (now.minusSeconds(savePeriod).isAfter(lastUpdateTime)) {
      lastUpdateTime = now;
      timestamps.entrySet().removeIf(e -> e.getValue().plusSeconds(savePeriod).isBefore(now));
      cache.keySet().removeIf(id -> !timestamps.containsKey(id));
    }
  }

  @Override
  public void putUser(Long id, UserEntity user) {
    cache.put(id, user);
    timestamps.put(id, Instant.now());
  }
}
