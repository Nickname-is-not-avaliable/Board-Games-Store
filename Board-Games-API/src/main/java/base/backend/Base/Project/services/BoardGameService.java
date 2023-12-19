package base.backend.Base.Project.services;

import base.backend.Base.Project.models.BoardGame;
import base.backend.Base.Project.models.dto.BoardGameDTO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BoardGameService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<BoardGame> getAllBoardGames() {
    String sql = "SELECT * FROM board_games";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(BoardGame.class)
    );
  }

  public BoardGame getBoardGameById(Integer id) {
    try {
      String sql = "SELECT * FROM board_games WHERE board_game_id = ?";
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(BoardGame.class),
        id
      );
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "BoardGame not found"
      );
    }
  }

  public List<BoardGame> getBoardGamesByCategory(String category) {
    String sql = "SELECT * FROM board_games WHERE category = ?";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(BoardGame.class),
      category
    );
  }

  public List<BoardGame> getBoardGamesByTitleContaining(String searchString) {
    String sql = "SELECT * FROM board_games WHERE LOWER(title) LIKE LOWER(?)";
    String searchPattern = "%" + searchString + "%";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(BoardGame.class),
      searchPattern
    );
  }

  public BoardGame createBoardGame(BoardGameDTO boardGameDTO) {
    String sql =
      "INSERT INTO board_games (title, description, publisher, release_date, category, price, preview_image, number_of_players, age, playtime, review_link, country_of_manufacture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING board_game_id";
    Integer newId = jdbcTemplate.queryForObject(
      sql,
      Integer.class,
      boardGameDTO.getTitle(),
      boardGameDTO.getDescription(),
      boardGameDTO.getPublisher(),
      boardGameDTO.getReleaseDate(),
      boardGameDTO.getCategory(),
      boardGameDTO.getPrice(),
      boardGameDTO.getPreviewImage(),
      boardGameDTO.getNumberOfPlayers(),
      boardGameDTO.getAge(),
      boardGameDTO.getPlaytime(),
      boardGameDTO.getReviewLink(),
      boardGameDTO.getCountryOfManufacture()
    );
    return getBoardGameById(newId);
  }

  public BoardGame updateBoardGame(Integer id, Map<String, Object> updates) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    StringBuilder sql = new StringBuilder("UPDATE board_games SET ");

    updates.forEach((key, value) -> {
      sql.append(key).append(" = :").append(key).append(", ");
      params.addValue(key, value);
    });

    sql.delete(sql.length() - 2, sql.length());
    sql.append(" WHERE board_game_id = :id");
    params.addValue("id", id);

    namedParameterJdbcTemplate.update(sql.toString(), params);
    return getBoardGameById(id);
  }

  public void deleteBoardGame(Integer id) {
    String sql = "DELETE FROM board_games WHERE board_game_id = ?";
    jdbcTemplate.update(sql, id);
  }
}
