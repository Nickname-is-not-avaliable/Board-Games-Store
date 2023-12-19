package base.backend.Base.Project.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board_games")
public class BoardGame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_game_id")
  private Integer boardGameId;

  private String title;

  @Column(length = 2048)
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
