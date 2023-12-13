package coursework.backend.controllers;

import coursework.backend.models.Rating;
import coursework.backend.models.dto.RatingDTO;
import coursework.backend.services.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ratings")
@Tag(
        name = "Ratings",
        description = "Operations for interacting with movie ratings"
)
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<RatingDTO> ratingDTOs = ratingService.getAllRatings().stream()
                .map(RatingDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratingDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable int id) {
        Optional<RatingDTO> ratingDTO = ratingService.getRatingById(id)
                .map(RatingDTO::new);
        return ratingDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/byMovieId/{movieId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByMovieId(@PathVariable Integer movieId) {
        List<RatingDTO> ratingsByMovieId = ratingService.getRatingsByMovieId(movieId).stream()
                .map(RatingDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ratingsByMovieId);
    }
    @PostMapping
    public ResponseEntity<Void> createRating(@RequestBody RatingDTO ratingDTO) {
        Rating rating = new Rating(ratingDTO);
        ratingService.createRating(rating);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable int id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
