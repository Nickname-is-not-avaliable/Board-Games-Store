package base.backend.Base.Project.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import base.backend.Base.Project.models.dto.BoardGameDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board_games")
public class BoardGame {
    public BoardGame(BoardGameDTO boardGameDTO) {
        this.id = boardGameDTO.getId();
        this.title = boardGameDTO.getTitle();
        this.description = boardGameDTO.getDescription();
        this.publisher = boardGameDTO.getPublisher();
        this.releaseDate = boardGameDTO.getReleaseDate();
        this.category = boardGameDTO.getCategory();
        this.price = boardGameDTO.getPrice();
        this.previewImage = boardGameDTO.getPreviewImage();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_game_id")
    private Integer id;
    private String title;
    private String description;
    private String publisher;
    private LocalDate releaseDate;
    private String category;
    private BigDecimal price;
    private String previewImage;
}

