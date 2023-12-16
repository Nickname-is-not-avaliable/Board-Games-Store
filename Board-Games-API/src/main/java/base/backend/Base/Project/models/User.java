package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Entity representing a user")
public class User {

  public User(UserDTO userDTO) {
    this.email = userDTO.getEmail();
    this.password = userDTO.getPassword();
    this.role = userDTO.getRole();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserRole role;

  public enum UserRole {
    USER,
    ADMIN,
  }
}
