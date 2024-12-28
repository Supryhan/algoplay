package problemslab.lx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewUsersFirstCache implements UserCache {

  Map<Long, UserEntity> integerUserEntityMap;

  Map<Long, Long> integerLongMap;

  private Long lastUpdateTime;
  private Long savePeriod;

  public NewUsersFirstCache() {
    lastUpdateTime = LocalDate.now().getLong();
    integerUserEntityMap = new HashMap<>();
    integerLongMap = new HashMap<>();
  }

  @Override
  public Map<Long, UserEntity> getSource() {
    return integerUserEntityMap;
  }

  @Override
  public Map<Long, Long> getSaveTime() {
    return integerLongMap;
  }

  public boolean isUser(int id) {
    updateCacheState();
    UserEntity userEntity = integerUserEntityMap.get(Integer.valueOf(id));
    return userEntity != null;
  }

  @Override
  public UserEntity getCachedUser(Long id) {
    updateCacheState();
    UserEntity userEntity = integerUserEntityMap.get(id);
    return userEntity;
  }

  @Override
  public void updateCacheState() {
    long now = 0;
    List<Long> listIdToRemove = new ArrayList<>();
    long removeIfLess = now - savePeriod;
    if (now - lastUpdateTime > savePeriod) {
      for (Map.Entry<Long, Long> entry : integerLongMap.entrySet()) {
        if (entry.getValue() < removeIfLess) {
          listIdToRemove.add(entry.getKey());
        }
      }
      for (Long id : listIdToRemove) {
        integerUserEntityMap.remove(id);
        integerLongMap.remove(id);
      }
    }
  }
}
