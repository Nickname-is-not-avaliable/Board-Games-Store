package coursework.backend.models.dto;

import coursework.backend.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Integer id;
    private Integer movieId;
    private Integer userId;
    private String fullName;
    private Boolean liked;
    private String comment;

    public RatingDTO(Rating rating) {
        this.id = rating.getId();
        this.movieId = rating.getMovieId();
        this.userId = rating.getUserId();
        this.fullName = rating.getUser().getFullName();
        this.liked = rating.getLiked();
        this.comment = rating.getComment();
    }

}
