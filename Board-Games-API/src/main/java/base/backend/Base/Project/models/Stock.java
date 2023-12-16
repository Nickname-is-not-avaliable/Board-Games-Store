package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.StockDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {
    public Stock(StockDTO stockDTO) {
        this.stockId = stockDTO.getStockId();
        this.storeId = stockDTO.getStoreId();
        this.boardGameId = stockDTO.getBoardGameId();
        this.quantity = stockDTO.getQuantity();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;
    @Column(name = "board_game_id")
    private Integer boardGameId;
    @Column(name = "store_id")
    private Integer storeId;
    @Column
    private Integer quantity;
}