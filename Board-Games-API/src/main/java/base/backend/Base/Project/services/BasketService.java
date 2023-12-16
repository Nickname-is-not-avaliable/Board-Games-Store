package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Basket;
import base.backend.Base.Project.repositories.BasketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    public Optional<Basket> getBasketById(Integer basketId) {
        return basketRepository.findById(basketId);
    }

    public List<Basket> getBasketsByUserId(Integer userId) {
        return basketRepository.findByUserId(userId);
    }

    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public void deleteBasket(Integer basketId) {
        basketRepository.deleteById(basketId);
    }

    private ResponseStatusException basketNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Basket not found");
    }
}
