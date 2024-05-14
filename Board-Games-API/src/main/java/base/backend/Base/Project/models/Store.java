package base.backend.Base.Project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Store {
    public Store(StoreDTO storeDTO) {
        this.storeId = storeDTO.getStoreId();
        this.address = storeDTO.getAddress();
        this.latitude = storeDTO.getLatitude();
        this.longitude = storeDTO.getLongitude();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;
    private String address;
    private Double latitude;
    private Double longitude;
}