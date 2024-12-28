package problemslab.lx;

import java.util.Map;

public interface UserCache {

  Map<Long, UserEntity> getSource();
  Map<Long, Long> getSaveTime();

  boolean isUser(Long id);

  UserEntity getCachedUser(Long id);

  void updateCacheState();

}
