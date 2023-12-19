package base.backend.Base.Project.models;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardGame {

  private Integer boardGameId;
  private String title;
  private String description;
  private String publisher;
  private String releaseDate;
  private String category;
  private BigDecimal price;
  private String previewImage;
  private Integer numberOfPlayers;
  private Integer age;
  private String playtime;
  private String reviewLink;
  private String countryOfManufacture;
}
