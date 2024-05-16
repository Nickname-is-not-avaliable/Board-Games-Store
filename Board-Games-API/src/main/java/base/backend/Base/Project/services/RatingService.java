package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Rating;
import base.backend.Base.Project.models.dto.RatingDTO;
import base.backend.Base.Project.repositories.RatingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> getRatingsByBoardGameId(Integer boardGameId) {
        return ratingRepository.findByBoardGameId(boardGameId);
    }

    public void createRating(RatingDTO ratingDTO) {
        Rating rating = new Rating(ratingDTO);
        rating.setDate(LocalDateTime.now());
        ratingRepository.save(rating);
    }

    public Rating updateRating(Integer id, Map<String, Object> updates) {
        Rating existingRating = ratingRepository
                .findById(id)
                .orElseThrow(this::ratingNotFound);

        if (updates.containsKey("boardGameId")) {
            existingRating.setBoardGameId((Integer) updates.get("boardGameId"));
        }

        if (updates.containsKey("userId")) {
            existingRating.setUserId((Integer) updates.get("userId"));
        }

        if (updates.containsKey("isLiked")) {
            existingRating.setLiked((Boolean) updates.get("isLiked"));
        }
        return ratingRepository.save(existingRating);
    }

    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    private ResponseStatusException ratingNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating not found");
    }
}
