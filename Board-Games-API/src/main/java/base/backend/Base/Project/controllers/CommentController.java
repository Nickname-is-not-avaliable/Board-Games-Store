package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Comment;
import base.backend.Base.Project.models.dto.CommentDTO;
import base.backend.Base.Project.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
@Tag(name = "Comments")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    List<Comment> comments = commentService.getAllComments();
    List<CommentDTO> commentDTOs = comments
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(commentDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer id) {
    Comment comment = commentService.getCommentById(id);
    if (comment != null) {
      return ResponseEntity.ok(convertToDTO(comment));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/by-board-game/{boardGameId}")
  public ResponseEntity<List<CommentDTO>> getCommentsByBoardGameId(
    @PathVariable Integer boardGameId
  ) {
    List<Comment> comments = commentService.getCommentsByBoardGameId(
      boardGameId
    );
    List<CommentDTO> commentDTOs = comments
      .stream()
      .map(this::convertToDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(commentDTOs);
  }

  @PostMapping
  public ResponseEntity<Void> createComment(
    @RequestBody CommentDTO commentDTO
  ) {
    commentService.createComment(commentDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
    commentService.deleteComment(id);
    return ResponseEntity.noContent().build();
  }

  private CommentDTO convertToDTO(Comment comment) {
    return new CommentDTO(comment);
  }
}
