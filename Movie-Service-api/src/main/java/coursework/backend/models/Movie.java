package coursework.backend.models;

import coursework.backend.models.dto.MovieDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movies")
@Schema(description = "Entity representing a movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer id;

    @Column()
    private String title;

    @Column(length = 2048)
    private String description;

    @Column()
    private String genre;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column()
    private String country;

    @Column
    private String directors;

    @Column(length = 2048)
    private String actors;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "languages")
    private String languages;

    @Column(name = "trailer_link")
    private String trailerLink;

    @Column(name = "preview_image")
    private String previewImage;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.description = movieDTO.getDescription();
        this.genre = movieDTO.getGenre();
        this.releaseYear = movieDTO.getReleaseYear();
        this.country = movieDTO.getCountry();
        this.directors = movieDTO.getDirectors();
        this.actors = movieDTO.getActors();
        this.runtime = movieDTO.getRuntime();
        this.languages = movieDTO.getLanguages();
        this.trailerLink = movieDTO.getTrailerLink();
        this.previewImage = movieDTO.getPreviewImage();
    }
}
