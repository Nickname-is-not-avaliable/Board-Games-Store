package coursework.backend.controllers;

import coursework.backend.models.User;
import coursework.backend.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@Tag(name = "Users", description = "Operations for interacting with users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable int id) {
    Optional<User> user = userService.getUserById(id);
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody User user) {
    if (userService.getUserByEmail(user.getEmail()) != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<User> updateUser(
    @PathVariable Integer id,
    @RequestBody Map<String, Object> updates
  ) {
    User savedUser = userService.updateUser(id, updates);
    return ResponseEntity.ok(savedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable int id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@RequestBody User userDTO) {
    User user = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
