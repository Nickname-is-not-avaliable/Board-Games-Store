package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Order;
import base.backend.Base.Project.models.dto.OrderDTO;
import java.time.LocalDateTime;
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
public class OrderService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<Order> getAllOrders() {
    String sql = "SELECT * FROM orders";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
  }

  public Order getOrderById(Integer orderId) {
    try {
      String sql = "SELECT * FROM orders WHERE order_id = ?";
      return jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(Order.class),
        orderId
      );
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Order not found"
      );
    }
  }

  public List<Order> getOrdersByUserId(Integer userId) {
    String sql = "SELECT * FROM orders WHERE user_id = ?";
    return jdbcTemplate.query(
      sql,
      new BeanPropertyRowMapper<>(Order.class),
      userId
    );
  }

  public Order createOrder(OrderDTO orderDTO) {
    String sql =
      "INSERT INTO orders (user_id, order_date, order_details, total_price, status) VALUES (?, ?, ?, ?, ?) RETURNING order_id";
    Integer newId = jdbcTemplate.queryForObject(
      sql,
      Integer.class,
      orderDTO.getUserId(),
      LocalDateTime.now(),
      orderDTO.getOrderDetails(),
      orderDTO.getTotalPrice(),
      orderDTO.getStatus().toString()
    );
    return getOrderById(newId);
  }

  public Order updateOrder(Integer orderId, Map<String, Object> updates) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    StringBuilder sql = new StringBuilder("UPDATE orders SET ");

    updates.forEach((key, value) -> {
      sql.append(key).append(" = :").append(key).append(", ");
      params.addValue(key, value);
    });

    sql.delete(sql.length() - 2, sql.length());
    sql.append(" WHERE order_id = :orderId");
    params.addValue("orderId", orderId);

    namedParameterJdbcTemplate.update(sql.toString(), params);
    return getOrderById(orderId);
  }

  public void deleteOrder(Integer orderId) {
    String sql = "DELETE FROM orders WHERE order_id = ?";
    jdbcTemplate.update(sql, orderId);
  }

}
