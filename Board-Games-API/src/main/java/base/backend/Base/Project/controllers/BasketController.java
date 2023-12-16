package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Basket;
import base.backend.Base.Project.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/baskets")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public ResponseEntity<List<Basket>> getAllBaskets() {
        List<Basket> baskets = basketService.getAllBaskets();
        return ResponseEntity.ok(baskets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Integer id) {
        Optional<Basket> basket = basketService.getBasketById(id);
        return basket
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Basket>> getBasketsByUserId(@PathVariable Integer userId) {
        List<Basket> baskets = basketService.getBasketsByUserId(userId);
        return ResponseEntity.ok(baskets);
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        Basket createdBasket = basketService.createBasket(basket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBasket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Integer id) {
        basketService.deleteBasket(id);
        return ResponseEntity.noContent().build();
    }
}
