package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.BoardGameOnBasket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameOnBasketDTO {
    public BoardGameOnBasketDTO(BoardGameOnBasket boardGameOnBasket) {
        this.boardGameOnBasketId = boardGameOnBasket.getBoardGameOnBasketId();
        this.basketId = boardGameOnBasket.getBasketId();
        this.boardGameId = boardGameOnBasket.getBoardGameId();
        this.quantity = boardGameOnBasket.getQuantity();
    }

    private Integer boardGameOnBasketId;
    private Integer basketId;
    private Integer boardGameId;
    private Integer quantity;
}