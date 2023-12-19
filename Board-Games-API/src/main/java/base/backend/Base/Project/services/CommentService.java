package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Comment;
import base.backend.Base.Project.models.dto.CommentDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Comment> getAllComments() {
    String sql = "SELECT * FROM comments";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class));
  }

  public Comment getCommentById(Integer id) {
    String sql = "SELECT * FROM comments WHERE comment_id = ?";
    return jdbcTemplate.queryForObject(
      sql,
      new BeanPropertyRowMapper<>(Comment.class),
      id
    );
  }

  public List<Comment> getCommentsByBoardGameId(Integer boardGameId) {
    String sql = "SELECT * FROM comments WHERE board_game_id = ?";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(Comment.class),
      boardGameId
    );
  }

  public void createComment(CommentDTO commentDTO) {
    String sql =
      "INSERT INTO comments (board_game_id, user_id, text, date) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(
      sql,
      commentDTO.getBoardGameId(),
      commentDTO.getUserId(),
      commentDTO.getText(),
      LocalDateTime.now()
    );
  }

  public void deleteComment(Integer id) {
    String sql = "DELETE FROM comments WHERE comment_id = ?";
    jdbcTemplate.update(sql, id);
  }
}
