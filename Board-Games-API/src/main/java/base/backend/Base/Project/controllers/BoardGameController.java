package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.BoardGame;
import base.backend.Base.Project.models.dto.BoardGameDTO;
import base.backend.Base.Project.services.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/board-games")
public class BoardGameController {

    private final BoardGameService boardGameService;

    @Autowired
    public BoardGameController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping
    public ResponseEntity<List<BoardGame>> getAllBoardGames() {
        List<BoardGame> boardGames = boardGameService.getAllBoardGames();
        return ResponseEntity.ok(boardGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> getBoardGameById(@PathVariable Integer id) {
        Optional<BoardGame> boardGame = boardGameService.getBoardGameById(id);
        return boardGame
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<BoardGame>> getBoardGamesByCategory(@PathVariable String category) {
        List<BoardGame> boardGames = boardGameService.getBoardGamesByCategory(category);
        return ResponseEntity.ok(boardGames);
    }

    @PostMapping
    public ResponseEntity<BoardGame> createBoardGame(@RequestBody BoardGameDTO boardGameDTO) {
        BoardGame newBoardGame = boardGameService.createBoardGame(boardGameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoardGame);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardGame> updateBoardGame(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        BoardGame updatedBoardGame = boardGameService.updateBoardGame(id, updates);
        return ResponseEntity.ok(updatedBoardGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGame(@PathVariable Integer id) {
        boardGameService.deleteBoardGame(id);
        return ResponseEntity.noContent().build();
    }
}
