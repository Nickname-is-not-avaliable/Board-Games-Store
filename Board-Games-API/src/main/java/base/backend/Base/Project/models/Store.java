package base.backend.Base.Project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Store {

  private Integer storeId;
  private String address;
  private Double latitude;
  private Double longitude;
}
