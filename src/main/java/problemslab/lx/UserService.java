package problemslab.lx;

public class UserService {
  public static void main(String[] args) {
    UserService userService = new UserService(new UserRepository() {
      @Override
      public UserEntity findUserById(Long id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("name");
        return userEntity;
      }
    }, new UserCacheImpl());
    var user = userService.findUserById(1L);
    System.out.println(user.toString());
  }
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
