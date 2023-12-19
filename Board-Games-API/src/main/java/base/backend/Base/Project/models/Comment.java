package base.backend.Base.Project.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Integer commentId;

  @Column(name = "board_game_id")
  private Integer boardGameId;

  @ManyToOne
  @JoinColumn(
    name = "board_game_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private BoardGame boardGame;

  @Column(name = "user_id")
  private Integer userId;

  @ManyToOne
  @JoinColumn(
    name = "user_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private User user;

  @Column(length = 2048)
  private String text;

  private LocalDateTime date;
}
