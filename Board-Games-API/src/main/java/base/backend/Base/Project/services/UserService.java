package base.backend.Base.Project.services;

import base.backend.Base.Project.repositories.UserRepository;
import base.backend.Base.Project.models.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Integer id) {
    return userRepository.findById(id);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void createUser(User user) {
    user.setPassword(
      org.apache.commons.codec.digest.DigestUtils.sha256Hex(user.getPassword())
    );
    userRepository.save(user);
  }

  public User updateUser(Integer id, Map<String, Object> updates) {
    User existingUser = userRepository
      .findById(id)
      .orElseThrow(this::userNotFound);

    if (updates.containsKey("email")) {
      existingUser.setEmail((String) updates.get("email"));
    }

    if (updates.containsKey("password")) {
      String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(
        (String) updates.get("password")
      );
      existingUser.setPassword(hashedPassword);
    }

    if (updates.containsKey("role")) {
      User.UserRole newRole = (User.UserRole) updates.get("role");
      existingUser.setRole(newRole);
    }
    return userRepository.save(existingUser);
  }

  public void deleteUser(Integer id) {
    userRepository.deleteById(id);
  }

  private ResponseStatusException userNotFound() {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
  }
}
