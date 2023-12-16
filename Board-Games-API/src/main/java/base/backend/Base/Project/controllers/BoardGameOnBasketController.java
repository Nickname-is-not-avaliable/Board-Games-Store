package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.BoardGameOnBasket;
import base.backend.Base.Project.models.dto.BoardGameOnBasketDTO;
import base.backend.Base.Project.services.BoardGameOnBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/board-games-on-basket")
public class BoardGameOnBasketController {

    private final BoardGameOnBasketService boardGameOnBasketService;

    @Autowired
    public BoardGameOnBasketController(BoardGameOnBasketService boardGameOnBasketService) {
        this.boardGameOnBasketService = boardGameOnBasketService;
    }

    @GetMapping
    public ResponseEntity<List<BoardGameOnBasket>> getAllBoardGamesOnBasket() {
        List<BoardGameOnBasket> boardGamesOnBasket = boardGameOnBasketService.getAllBoardGamesOnBasket();
        return ResponseEntity.ok(boardGamesOnBasket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameOnBasket> getBoardGameOnBasketById(@PathVariable Integer id) {
        Optional<BoardGameOnBasket> boardGameOnBasket = boardGameOnBasketService.getBoardGameOnBasketById(id);
        return boardGameOnBasket
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-basket/{basketId}")
    public ResponseEntity<List<BoardGameOnBasket>> getBoardGamesOnBasketByBasketId(@PathVariable Integer basketId) {
        List<BoardGameOnBasket> boardGamesOnBasket = boardGameOnBasketService.getBoardGamesOnBasketByBasketId(basketId);
        return ResponseEntity.ok(boardGamesOnBasket);
    }

    @PostMapping
    public ResponseEntity<BoardGameOnBasket> createBoardGameOnBasket(@RequestBody BoardGameOnBasketDTO boardGameOnBasketDTO) {
        BoardGameOnBasket newBoardGameOnBasket = boardGameOnBasketService.createBoardGameOnBasket(boardGameOnBasketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoardGameOnBasket);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardGameOnBasket> updateBoardGameOnBasket(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        BoardGameOnBasket updatedBoardGameOnBasket = boardGameOnBasketService.updateBoardGameOnBasket(id, updates);
        return ResponseEntity.ok(updatedBoardGameOnBasket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGameOnBasket(@PathVariable Integer id) {
        boardGameOnBasketService.deleteBoardGameOnBasket(id);
        return ResponseEntity.noContent().build();
    }
}
