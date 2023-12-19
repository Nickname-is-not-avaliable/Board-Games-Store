package base.backend.Base.Project.services;

import base.backend.Base.Project.models.User;
import base.backend.Base.Project.models.dto.UserDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<User> getAllUsers() {
    String sql = "SELECT * FROM users";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
  }

  public User getUserById(Integer id) {
    try {
      String sql = "SELECT * FROM users WHERE user_id = ?";
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(User.class),
        id
      );
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
  }

  public User authenticate(String email, String password) {
    String hashedPassword = DigestUtils.sha256Hex(password);
    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
    try {
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(User.class),
        email,
        hashedPassword
      );
    } catch (Exception e) {
      return null;
    }
  }

  public User createUser(UserDTO userDTO) {
    String hashedPassword = DigestUtils.sha256Hex(userDTO.getPassword());
    String sql =
      "INSERT INTO users (email, username, password, date_of_registration, role) VALUES (?, ?, ?, ?, ?::user_role) RETURNING user_id";
    Integer newId = jdbcTemplate.queryForObject(
      sql,
      Integer.class,
      userDTO.getEmail(),
      userDTO.getUsername(),
      hashedPassword,
      LocalDate.now(),
      userDTO.getRole().toString()
    );
    return getUserById(newId);
  }

  public User updateUser(Integer id, Map<String, Object> updates) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    StringBuilder sql = new StringBuilder("UPDATE users SET ");

    updates.forEach((key, value) -> {
      if ("password".equals(key)) {
        value = DigestUtils.sha256Hex((String) value);
      }
      sql.append(key).append(" = :").append(key).append(", ");
      params.addValue(key, value);
    });

    sql.delete(sql.length() - 2, sql.length());
    sql.append(" WHERE user_id = :userId");
    params.addValue("userId", id);

    namedParameterJdbcTemplate.update(sql.toString(), params);
    return getUserById(id);
  }

  public void deleteUser(Integer id) {
    String sql = "DELETE FROM users WHERE user_id = ?";
    jdbcTemplate.update(sql, id);
  }
}
