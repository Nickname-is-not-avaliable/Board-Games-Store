package base.backend.Base.Project.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@Schema(description = "Entity representing a user")
public class User {

  private Integer userId;
  private String email;
  private String username;
  private String password;
  private LocalDate dateOfRegistration;
  private UserRole role;

  public enum UserRole {
    USER,
    ADMIN,
  }
}
