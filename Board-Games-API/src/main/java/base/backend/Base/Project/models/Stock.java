package base.backend.Base.Project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Stock {

  private Integer stockId;
  private Integer boardGameId;
  private BoardGame boardGame;
  private Integer storeId;
  private Store store;
  private Integer quantity;
}
