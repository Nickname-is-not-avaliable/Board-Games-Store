package coursework.backend.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Entity representing a user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer id;

  @Column(unique = true)
  private String email;

  @Column()
  private String password;

  @Column()
  private String fullName;

  @Column
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserRole role;

  public enum UserRole {
    USER,
    ADMIN,
  }
}
