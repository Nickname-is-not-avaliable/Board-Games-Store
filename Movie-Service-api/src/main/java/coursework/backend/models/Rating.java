package coursework.backend.models;

import coursework.backend.models.dto.RatingDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ratings")
@Schema(description = "Entity representing a movie rating by a user")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer id;

    @Column(name = "movie_id")
    private Integer movieId;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column()
    private Boolean liked;

    @Column(length = 2048)
    private String comment;

    public Rating(RatingDTO ratingDTO) {
        this.movieId = ratingDTO.getMovieId();
        this.userId = ratingDTO.getUserId();
        this.liked = ratingDTO.getLiked();
        this.comment = ratingDTO.getComment();
    }
}
