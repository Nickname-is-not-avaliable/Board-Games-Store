package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.User;
import base.backend.Base.Project.models.dto.UserDTO;
import base.backend.Base.Project.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserDTO> userDTOs = users
      .stream()
      .map(UserDTO::new)
      .collect(Collectors.toList());
    return ResponseEntity.ok(userDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
    Optional<User> user = userService.getUserById(id);
    return user
      .map(u -> ResponseEntity.ok(new UserDTO(u)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
    User user = new User(userDTO);
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
}
