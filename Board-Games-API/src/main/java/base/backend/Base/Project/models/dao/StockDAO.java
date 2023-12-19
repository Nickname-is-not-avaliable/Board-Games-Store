package base.backend.Base.Project.models.dao;

import base.backend.Base.Project.models.Stock;
import base.backend.Base.Project.models.dto.StockDTO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<Stock> getAllStocks() {
    String sql = "SELECT * FROM stock";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class));
  }

  public Stock getStockById(Integer stockId) {
    try {
      String sql = "SELECT * FROM stock WHERE stock_id = ?";
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(Stock.class),
        stockId
      );
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Stock not found"
      );
    }
  }

  public List<Stock> getStocksByBoardGameId(Integer boardGameId) {
    String sql = "SELECT * FROM stock WHERE board_game_id = ?";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(Stock.class),
      boardGameId
    );
  }

  public List<Stock> getStocksByStoreId(Integer storeId) {
    String sql = "SELECT * FROM stock WHERE store_id = ?";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(Stock.class),
      storeId
    );
  }

  public Stock createStock(StockDTO stockDTO) {
    String sql =
      "INSERT INTO stock (board_game_id, store_id, quantity) VALUES (?, ?, ?) RETURNING stock_id";
    Integer newId = jdbcTemplate.queryForObject(
      sql,
      Integer.class,
      stockDTO.getBoardGameId(),
      stockDTO.getStoreId(),
      stockDTO.getQuantity()
    );
    return getStockById(newId);
  }

  public Stock updateStock(Integer stockId, Map<String, Object> updates) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    StringBuilder sql = new StringBuilder("UPDATE stock SET ");

    updates.forEach((key, value) -> {
      sql.append(key).append(" = :").append(key).append(", ");
      params.addValue(key, value);
    });

    sql.delete(sql.length() - 2, sql.length());
    sql.append(" WHERE stock_id = :stockId");
    params.addValue("stockId", stockId);

    namedParameterJdbcTemplate.update(sql.toString(), params);
    return getStockById(stockId);
  }

  public void deleteStock(Integer stockId) {
    String sql = "DELETE FROM stock WHERE stock_id = ?";
    jdbcTemplate.update(sql, stockId);
  }
}
