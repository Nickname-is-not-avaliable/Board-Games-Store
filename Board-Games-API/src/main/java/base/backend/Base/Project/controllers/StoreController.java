package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Store;
import base.backend.Base.Project.models.dto.StoreDTO;
import base.backend.Base.Project.models.dao.StoreDAO;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/stores")
@Tag(name = "Stores")
public class StoreController {

  private final StoreDAO storeDAO;

  @Autowired
  public StoreController(StoreDAO storeDAO) {
    this.storeDAO = storeDAO;
  }

  @GetMapping
  public ResponseEntity<List<StoreDTO>> getAllStores() {
    List<Store> stores = storeDAO.getAllStores();
    List<StoreDTO> storeDTOs = stores
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(storeDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StoreDTO> getStoreById(@PathVariable Integer id) {
    Store store = storeDAO.getStoreById(id);
    if (store != null) {
      return ResponseEntity.ok(convertToDTO(store));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
    Store newStore = storeDAO.createStore(storeDTO);
    StoreDTO newStoreDTO = convertToDTO(newStore);
    return ResponseEntity.status(HttpStatus.CREATED).body(newStoreDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
    storeDAO.deleteStore(id);
    return ResponseEntity.noContent().build();
  }

  private StoreDTO convertToDTO(Store store) {
    return new StoreDTO(store);
  }
}
