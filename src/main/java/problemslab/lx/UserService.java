package problemslab.lx;

public class UserService {
  private UserCache userCache;
  private UserRepository userRepository;

  public UserService(UserRepository userRepository, UserCache userCache) {
    this.userRepository = userRepository;
    this.userCache = userCache;
  }

  public UserEntity findUserById(Long id) {
    UserEntity user = userCache.getUser(id);
    if (user == null) {
      user = userRepository.findUserById(id);
      if (user != null) {
        userCache.putUser(id, user);
      }
    }
    return user;
  }
}
