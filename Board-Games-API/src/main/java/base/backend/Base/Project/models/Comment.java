package base.backend.Base.Project.models;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {

  private Integer commentId;
  private Integer boardGameId;
  private BoardGame boardGame;
  private Integer userId;
  private User user;
  private String text;
  private LocalDateTime date;
}
