package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.BasketDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "baskets")
public class Basket {
    public Basket(BasketDTO basketDTO) {
        this.basketId = basketDTO.getBasketId();
        this.userId = basketDTO.getUserId();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Integer basketId;

    @Column(name = "user_id")
    private Integer userId;
}