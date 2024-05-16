package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Rating;
import base.backend.Base.Project.models.dto.RatingDTO;
import base.backend.Base.Project.services.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/ratings")
@Tag(name = "Ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        List<RatingDTO> ratingDTOs = ratings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratingDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable Integer id) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        return rating
                .map(r -> ResponseEntity.ok(convertToDTO(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-board-game/{boardGameId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByBoardGameId(@PathVariable Integer boardGameId) {
        List<Rating> ratings = ratingService.getRatingsByBoardGameId(boardGameId);
        List<RatingDTO> ratingDTOs = ratings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratingDTOs);
    }


    @PostMapping
    public ResponseEntity<Void> createRating(@RequestBody RatingDTO ratingDTO) {
        ratingService.createRating(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Rating updatedRating = ratingService.updateRating(id, updates);
        RatingDTO updatedRatingDTO = convertToDTO(updatedRating);
        return ResponseEntity.ok(updatedRatingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    private RatingDTO convertToDTO(Rating rating) {
        return new RatingDTO(rating);
    }
}
