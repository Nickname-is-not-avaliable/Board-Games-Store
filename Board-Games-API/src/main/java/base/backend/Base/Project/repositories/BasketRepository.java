package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findByUserId(Integer userId);
}
