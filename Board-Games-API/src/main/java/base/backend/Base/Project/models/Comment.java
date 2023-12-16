package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    public Comment(CommentDTO commentDTO) {
        this.commentId = commentDTO.getCommentId();
        this.boardGameId = commentDTO.getBoardGameId();
        this.userId = commentDTO.getUserId();
        this.text = commentDTO.getText();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;
    @Column(name = "board_game_id")
    private Integer boardGameId;
    @Column(name = "user_id")
    private Integer userId;
    @Column
    private String text;
}