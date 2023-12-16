package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Basket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {
    public BasketDTO(Basket basket) {
        this.basketId = basket.getBasketId();
        this.userId = basket.getUserId();
    }

    private Integer basketId;
    private Integer userId;
}