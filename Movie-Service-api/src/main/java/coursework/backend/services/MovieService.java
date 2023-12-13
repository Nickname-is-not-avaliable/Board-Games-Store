package coursework.backend.services;

import coursework.backend.models.Movie;
import coursework.backend.repositories.MovieRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  public Optional<Movie> getMovieById(Integer id) {
    return movieRepository.findById(id);
  }

  public List<Movie> getMoviesByGenre(String genre) {
    return movieRepository.findByGenre(genre);
  }

  public List<Movie> getMoviesByTitleContaining(String searchString) {
    return movieRepository.findByTitleContainingIgnoreCase(searchString);
  }

  public void createMovie(Movie movie) {
    movieRepository.save(movie);
  }

  public Movie updateMovie(Integer id, Map<String, Object> updates) {
    Movie existingMovie = movieRepository
            .findById(id)
            .orElseThrow(this::movieNotFound);

    if (updates.containsKey("title")) existingMovie.setTitle((String) updates.get("title"));
    if (updates.containsKey("description")) existingMovie.setDescription((String) updates.get("description"));
    if (updates.containsKey("genre")) existingMovie.setGenre((String) updates.get("genre"));
    if (updates.containsKey("releaseYear")) existingMovie.setReleaseYear((Integer) updates.get("releaseYear"));
    if (updates.containsKey("trailerLink")) existingMovie.setTrailerLink((String) updates.get("trailerLink"));
    if (updates.containsKey("previewImage")) existingMovie.setPreviewImage((String) updates.get("previewImage"));

    if (updates.containsKey("country")) existingMovie.setCountry((String) updates.get("country"));
    if (updates.containsKey("directors")) existingMovie.setDirectors((String) updates.get("directors"));
    if (updates.containsKey("actors")) existingMovie.setActors((String) updates.get("actors"));
    if (updates.containsKey("runtime")) existingMovie.setRuntime((Integer) updates.get("runtime"));
    if (updates.containsKey("languages")) existingMovie.setLanguages((String) updates.get("languages"));

    return movieRepository.save(existingMovie);
  }

  public void deleteMovie(Integer id) {
    movieRepository.deleteById(id);
  }

  private ResponseStatusException movieNotFound() {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
  }
}
