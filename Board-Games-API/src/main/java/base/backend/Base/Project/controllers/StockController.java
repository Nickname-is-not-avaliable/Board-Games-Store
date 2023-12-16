package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Stock;
import base.backend.Base.Project.models.dto.StockDTO;
import base.backend.Base.Project.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-board-game/{boardGameId}")
    public ResponseEntity<List<Stock>> getStocksByBoardGameId(@PathVariable Integer boardGameId) {
        List<Stock> stocks = stockService.getStocksByBoardGameId(boardGameId);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<Stock>> getStocksByStoreId(@PathVariable Integer storeId) {
        List<Stock> stocks = stockService.getStocksByStoreId(storeId);
        return ResponseEntity.ok(stocks);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody StockDTO stockDTO) {
        Stock newStock = stockService.createStock(stockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Stock updatedStock = stockService.updateStock(id, updates);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
