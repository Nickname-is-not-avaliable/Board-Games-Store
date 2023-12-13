package coursework.backend.models.dto;

import coursework.backend.models.Movie;
import coursework.backend.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Integer id;
    private String title;
    private String description;
    private String genre;
    private Integer releaseYear;
    private String country;
    private String directors;
    private String actors;
    private Integer runtime;
    private String languages;
    private String trailerLink;
    private String previewImage;
    private Double positiveReviewsPercentage;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genre = movie.getGenre();
        this.releaseYear = movie.getReleaseYear();
        this.country = movie.getCountry();
        this.directors = movie.getDirectors();
        this.actors = movie.getActors();
        this.runtime = movie.getRuntime();
        this.languages = movie.getLanguages();
        this.trailerLink = movie.getTrailerLink();
        this.previewImage = movie.getPreviewImage();
        if (movie.getRatings() != null && !movie.getRatings().isEmpty()) {
            long likedCount = movie.getRatings().stream().filter(Rating::getLiked).count();
            long totalRatings = movie.getRatings().size();
            this.positiveReviewsPercentage = (double) likedCount / totalRatings * 100;
        } else {
            this.positiveReviewsPercentage = 0.0;
        }
    }
}
