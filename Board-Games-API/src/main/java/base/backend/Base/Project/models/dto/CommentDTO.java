package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.boardGameId = comment.getBoardGameId();
        this.userId = comment.getUserId();
        this.text = comment.getText();
    }

    private Integer commentId;
    private Integer boardGameId;
    private Integer userId;
    private String text;
}