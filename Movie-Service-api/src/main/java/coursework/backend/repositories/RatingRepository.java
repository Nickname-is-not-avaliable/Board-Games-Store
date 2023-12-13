package coursework.backend.repositories;

import coursework.backend.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByMovieId(Integer movieId);
}
