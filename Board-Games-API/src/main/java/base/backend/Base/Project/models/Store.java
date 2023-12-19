package base.backend.Base.Project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_id")
  private Integer storeId;

  private String address;
  private Double latitude;
  private Double longitude;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Stock> stocks;
}
