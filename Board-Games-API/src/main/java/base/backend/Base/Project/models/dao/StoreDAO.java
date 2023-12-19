package base.backend.Base.Project.models.dao;

import base.backend.Base.Project.models.Store;
import base.backend.Base.Project.models.dto.StoreDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StoreDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Store> getAllStores() {
    String sql = "SELECT * FROM stores";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Store.class));
  }

  public Store getStoreById(Integer id) {
    try {
      String sql = "SELECT * FROM stores WHERE store_id = ?";
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(Store.class),
        id
      );
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Store not found"
      );
    }
  }

  public Store createStore(StoreDTO storeDTO) {
    String sql =
      "INSERT INTO stores (address, latitude, longitude) VALUES (?, ?, ?) RETURNING store_id";
    Integer newId = jdbcTemplate.queryForObject(
      sql,
      Integer.class,
      storeDTO.getAddress(),
      storeDTO.getLatitude(),
      storeDTO.getLongitude()
    );
    return getStoreById(newId);
  }

  public void deleteStore(Integer id) {
    String sql = "DELETE FROM stores WHERE store_id = ?";
    jdbcTemplate.update(sql, id);
  }
}
