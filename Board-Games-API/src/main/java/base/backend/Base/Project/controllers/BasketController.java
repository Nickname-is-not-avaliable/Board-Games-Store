package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Basket;
import base.backend.Base.Project.models.dto.BasketDTO;
import base.backend.Base.Project.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/baskets")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public ResponseEntity<List<BasketDTO>> getAllBaskets() {
        List<Basket> baskets = basketService.getAllBaskets();
        List<BasketDTO> basketDTOs = baskets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(basketDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketDTO> getBasketById(@PathVariable Integer id) {
        Optional<Basket> optionalBasket = basketService.getBasketById(id);
        return optionalBasket
                .map(basket -> ResponseEntity.ok(convertToDTO(basket)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<BasketDTO>> getBasketsByUserId(@PathVariable Integer userId) {
        List<Basket> baskets = basketService.getBasketsByUserId(userId);
        List<BasketDTO> basketDTOs = baskets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(basketDTOs);
    }

    @PostMapping
    public ResponseEntity<BasketDTO> createBasket(@RequestBody BasketDTO basketDTO) {
        Basket createdBasket = basketService.createBasket(convertToEntity(basketDTO));
        BasketDTO createdBasketDTO = convertToDTO(createdBasket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBasketDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Integer id) {
        basketService.deleteBasket(id);
        return ResponseEntity.noContent().build();
    }

    private BasketDTO convertToDTO(Basket basket) {
        return new BasketDTO(basket);
    }

    private Basket convertToEntity(BasketDTO basketDTO) {
        return new Basket(basketDTO);
    }
}
