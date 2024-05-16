package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    public RatingDTO(Rating rating) {
        this.ratingId = rating.getRatingId();
        this.boardGameId = rating.getBoardGameId();
        this.userId = rating.getUserId();
        this.date = rating.getDate();
        this.username = rating.getUser().getUsername();
        this.liked = rating.getLiked();
    }

    private Integer ratingId;
    private Integer boardGameId;
    private Integer userId;
    private String username;
    private LocalDateTime date;
    private Boolean liked;
}