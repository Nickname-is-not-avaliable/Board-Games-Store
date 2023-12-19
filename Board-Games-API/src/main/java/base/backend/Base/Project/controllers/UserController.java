package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.User;
import base.backend.Base.Project.models.dto.UserDTO;
import base.backend.Base.Project.models.dao.UserDAO;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@Tag(name = "Users")
public class UserController {

  private final UserDAO userDAO;

  @Autowired
  public UserController(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<User> users = userDAO.getAllUsers();
    List<UserDTO> userDTOs = users
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(userDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
    User user = userDAO.getUserById(id);
    if (user != null) {
      return ResponseEntity.ok(convertToDTO(user));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@RequestBody User userDTO) {
    User user = userDAO.authenticate(
      userDTO.getEmail(),
      userDTO.getPassword()
    );
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    User newUser = userDAO.createUser(userDTO);
    UserDTO newUserDTO = convertToDTO(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(
    @PathVariable Integer id,
    @RequestBody Map<String, Object> updates
  ) {
    User updatedUser = userDAO.updateUser(id, updates);
    UserDTO updatedUserDTO = convertToDTO(updatedUser);
    return ResponseEntity.ok(updatedUserDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    userDAO.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  private UserDTO convertToDTO(User user) {
    return new UserDTO(user);
  }
}
