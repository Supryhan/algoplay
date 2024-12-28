package problemslab.lx;

public interface UserCache {

  boolean containsUser(Long id);

  UserEntity getUser(Long id);

  void evictExpiredEntries();


  void putUser(Long id, UserEntity user);

}
