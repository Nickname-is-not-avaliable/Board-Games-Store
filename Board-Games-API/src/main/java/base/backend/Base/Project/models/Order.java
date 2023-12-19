package base.backend.Base.Project.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

  private Integer orderId;
  private Integer userId;
  private User user;
  private LocalDateTime orderDate;
  private String orderDetails;
  private BigDecimal totalPrice;
  private OrderStatus status;

  public enum OrderStatus {
    OPENED,
    DELIVERY,
    CONFIRMED,
    CANCELLED,
    PREORDER,
    CART,
  }
}
