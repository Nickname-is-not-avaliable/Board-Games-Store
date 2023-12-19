package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.BoardGame;
import base.backend.Base.Project.models.dto.BoardGameDTO;
import base.backend.Base.Project.services.BoardGameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/board-games")
@Tag(name = "BoardGames")
public class BoardGameController {

  private final BoardGameService boardGameService;

  @Autowired
  public BoardGameController(BoardGameService boardGameService) {
    this.boardGameService = boardGameService;
  }

  @GetMapping
  public ResponseEntity<List<BoardGameDTO>> getAllBoardGames() {
    List<BoardGame> boardGames = boardGameService.getAllBoardGames();
    List<BoardGameDTO> boardGameDTOs = boardGames
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(boardGameDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BoardGameDTO> getBoardGameById(
    @PathVariable Integer id
  ) {
    BoardGame boardGame = boardGameService.getBoardGameById(id);
    if (boardGame != null) {
      return ResponseEntity.ok(convertToDTO(boardGame));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/by-category/{category}")
  public ResponseEntity<List<BoardGameDTO>> getBoardGamesByCategory(
    @PathVariable String category
  ) {
    List<BoardGame> boardGames = boardGameService.getBoardGamesByCategory(
      category
    );
    List<BoardGameDTO> boardGameDTOs = boardGames
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(boardGameDTOs);
  }

  @PostMapping
  public ResponseEntity<BoardGameDTO> createBoardGame(
    @RequestBody BoardGameDTO boardGameDTO
  ) {
    BoardGame newBoardGame = boardGameService.createBoardGame(boardGameDTO);
    BoardGameDTO newBoardGameDTO = convertToDTO(newBoardGame);
    return ResponseEntity.status(HttpStatus.CREATED).body(newBoardGameDTO);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BoardGameDTO> updateBoardGame(
    @PathVariable Integer id,
    @RequestBody Map<String, Object> updates
  ) {
    BoardGame updatedBoardGame = boardGameService.updateBoardGame(id, updates);
    BoardGameDTO updatedBoardGameDTO = convertToDTO(updatedBoardGame);
    return ResponseEntity.ok(updatedBoardGameDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBoardGame(@PathVariable Integer id) {
    boardGameService.deleteBoardGame(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<BoardGameDTO>> getBoardGamesByTitleContaining(
    @RequestParam String searchString
  ) {
    List<BoardGameDTO> movieDTOs = boardGameService
      .getBoardGamesByTitleContaining(searchString)
      .stream()
      .map(BoardGameDTO::new)
      .collect(Collectors.toList());
    return ResponseEntity.ok(movieDTOs);
  }

  private BoardGameDTO convertToDTO(BoardGame boardGame) {
    return new BoardGameDTO(boardGame);
  }
}
