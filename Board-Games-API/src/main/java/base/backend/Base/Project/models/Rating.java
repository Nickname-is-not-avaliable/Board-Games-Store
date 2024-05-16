package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.RatingDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {
    public Rating(RatingDTO ratingDTO) {
        this.ratingId = ratingDTO.getRatingId();
        this.boardGameId = ratingDTO.getBoardGameId();
        this.userId = ratingDTO.getUserId();
        this.date = ratingDTO.getDate();
        this.liked = ratingDTO.getLiked();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer ratingId;
    @Column(name = "board_game_id")
    private Integer boardGameId;
    @ManyToOne
    @JoinColumn(name = "board_game_id", nullable = false, insertable = false, updatable = false)
    private BoardGame boardGame;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    @Column(length = 2048)
    private LocalDateTime date;
    private Boolean liked;

}
