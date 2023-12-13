package coursework.backend.services;

import coursework.backend.models.Rating;
import coursework.backend.repositories.RatingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

  private final RatingRepository ratingRepository;

  public RatingService(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  public List<Rating> getAllRatings() {
    return ratingRepository.findAll();
  }

  public List<Rating> getRatingsByMovieId(Integer movieId) {
    return ratingRepository.findByMovieId(movieId);
  }

  public Optional<Rating> getRatingById(Integer id) {
    return ratingRepository.findById(id);
  }

  public void createRating(Rating rating) {
    ratingRepository.save(rating);
  }

  public void deleteRating(Integer id) {
    ratingRepository.deleteById(id);
  }
}
