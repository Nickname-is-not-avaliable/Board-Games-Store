package base.backend.Base.Project.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Entity representing a user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  private String email;
  private String username;
  private String password;
  private LocalDate dateOfRegistration;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  public enum UserRole {
    USER,
    ADMIN,
  }
}
