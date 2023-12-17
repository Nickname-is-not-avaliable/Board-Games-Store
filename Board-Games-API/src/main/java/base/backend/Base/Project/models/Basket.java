package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.BasketDTO;
import base.backend.Base.Project.models.dto.UserDTO;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
}