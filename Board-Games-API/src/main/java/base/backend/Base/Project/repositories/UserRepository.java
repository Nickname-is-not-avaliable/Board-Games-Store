package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String username);
}
