package coursework.backend.controllers;

import coursework.backend.models.Movie;
import coursework.backend.models.dto.MovieDTO;
import coursework.backend.services.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/movies")
@Tag(name = "Movies", description = "Operations for interacting with movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movieDTOs = movieService.getAllMovies().stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable int id) {
        Optional<MovieDTO> movieDTO = movieService.getMovieById(id)
                .map(MovieDTO::new);
        return movieDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDTO>> getMostReviewedMovies() {
        List<MovieDTO> movieDTOs = movieService.getAllMovies().stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/best")
    public ResponseEntity<List<MovieDTO>> getBestRatedMovies() {
        List<MovieDTO> movieDTOs = movieService.getAllMovies().stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(@RequestParam String category) {
        List<MovieDTO> moviesByCategory = movieService.getMoviesByGenre(category).stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(moviesByCategory);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> getMoviesByTitleContaining(@RequestParam String searchString) {
        List<MovieDTO> movieDTOs = movieService.getMoviesByTitleContaining(searchString).stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> createMovie(@RequestBody MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);
        movieService.createMovie(movie);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Movie savedMovie = movieService.updateMovie(id, updates);
        return ResponseEntity.ok(savedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}