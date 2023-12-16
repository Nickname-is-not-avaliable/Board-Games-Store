package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

  private String email;
  private String password;
  private User.UserRole role;

  public UserDTO(User user) {
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.role = user.getRole();
  }
}
