package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.BoardGame;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameDTO {
    public BoardGameDTO(BoardGame boardGame) {
        this.id = boardGame.getId();
        this.title = boardGame.getTitle();
        this.description = boardGame.getDescription();
        this.publisher = boardGame.getPublisher();
        this.releaseDate = boardGame.getReleaseDate();
        this.category = boardGame.getCategory();
        this.price = boardGame.getPrice();
    }

    private Integer id;
    private String title;
    private String description;
    private String publisher;
    private LocalDate releaseDate;
    private String category;
    private BigDecimal price;
}
