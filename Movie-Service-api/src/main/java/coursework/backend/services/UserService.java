package coursework.backend.services;

import coursework.backend.models.User;
import coursework.backend.repositories.UserRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
      User.UserRole newRole = User.UserRole.valueOf(
        (String) updates.get("role")
      );
      existingUser.setRole(newRole);
    }

    if (updates.containsKey("fullName")) {
      existingUser.setFullName((String) updates.get("fullName"));
    }

    if (updates.containsKey("dateOfBirth")) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate dateOfBirth = LocalDate.parse(
        (String) updates.get("dateOfBirth"),
        formatter
      );
      existingUser.setDateOfBirth(dateOfBirth);
    }

    return userRepository.save(existingUser);
  }

  public void deleteUser(Integer id) {
    userRepository.deleteById(id);
  }

  private ResponseStatusException userNotFound() {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
  }

  public User authenticate(String email, String password) {
    List<User> users = getAllUsers();

    for (User user : users) {
      if (user.getEmail().equals(email) && user.getPassword().equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password))) {
        return user;
      }
    }

    return null;
  }
}
