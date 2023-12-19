package base.backend.Base.Project.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "user_id")
  private Integer userId;

  @ManyToOne
  @JoinColumn(
    name = "user_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private User user;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "order_details")
  private String orderDetails;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
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
