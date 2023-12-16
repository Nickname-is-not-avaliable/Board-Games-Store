package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.BoardGameOnBasketDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board_game_on_basket")
public class BoardGameOnBasket {
    public BoardGameOnBasket(BoardGameOnBasketDTO boardGameOnBasketDTO) {
        this.boardGameOnBasketId = boardGameOnBasketDTO.getBoardGameOnBasketId();
        this.basketId = boardGameOnBasketDTO.getBasketId();
        this.boardGameId = boardGameOnBasketDTO.getBoardGameId();
        this.quantity = boardGameOnBasketDTO.getQuantity();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_game_on_basket_id")
    private Integer boardGameOnBasketId;
    @Column(name = "basket_id")
    private Integer basketId;
    @Column(name = "board_game_id")
    private Integer boardGameId;
    private Integer quantity;
}