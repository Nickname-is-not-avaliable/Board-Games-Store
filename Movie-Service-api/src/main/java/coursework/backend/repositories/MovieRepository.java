package coursework.backend.repositories;

import coursework.backend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleContainingIgnoreCase(String searchString);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.ratings r GROUP BY m.id ORDER BY COUNT(r) DESC")
    List<Movie> findMoviesByMostReviews();

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.ratings r GROUP BY m.id ORDER BY AVG(CASE WHEN r.liked = true THEN 1 ELSE 0 END) DESC")
    List<Movie> findMoviesByBestRating();


    List<Movie> findByGenre(String genre);
}
