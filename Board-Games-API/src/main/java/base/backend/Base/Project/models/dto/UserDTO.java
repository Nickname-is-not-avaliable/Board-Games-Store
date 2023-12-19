package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.User;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

  public UserDTO(User user) {
    this.userId = user.getUserId();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.role = user.getRole();
    this.username = user.getUsername();
    this.dateOfRegistration = user.getDateOfRegistration();
  }

  private Integer userId;
  private String email;
  private String password;
  private User.UserRole role;
  private String username;
  private LocalDate dateOfRegistration;
}
