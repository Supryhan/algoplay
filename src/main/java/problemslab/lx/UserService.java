package problemslab.lx;

public class UserService {

  UserCache userCache;
  boolean isCacheUsed = true;

  private UserRepository userRepository;

  public UserService(UserRepository userRepository, UserCache userCache) {
    this.userRepository = userRepository;
    this.userCache = userCache;
  }

  public UserEntity findUserById(Long id) {
    if (isCacheUsed) {
      userCache.updateCacheState();
      boolean isUser = userCache.isUser(id);
      if (isUser) {
        return userCache.getCachedUser(id);
      } else {
        return userRepository.findUserById(id);
      }
    } else return null;
  }
}
