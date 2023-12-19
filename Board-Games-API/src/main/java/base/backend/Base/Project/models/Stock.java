package base.backend.Base.Project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stock_id")
  private Integer stockId;

  @Column(name = "board_game_id")
  private Integer boardGameId;

  @ManyToOne
  @JoinColumn(
    name = "board_game_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private BoardGame boardGame;

  @Column(name = "store_id")
  private Integer storeId;

  @ManyToOne
  @JoinColumn(
    name = "store_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private Store store;

  @Column
  private Integer quantity;
}
