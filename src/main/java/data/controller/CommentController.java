package data.controller;

import data.dto.CommentDto;
import data.service.CommentService;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final JwtProvider jwtProvider;

    @Autowired
    public CommentController(CommentService commentService, JwtProvider jwtProvider) {
        this.commentService = commentService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody CommentDto commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setUserId(userId);
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/purchase/{purchase_id}")
    public ResponseEntity<?> findCommentById(
            @PathVariable int purchase_id
    ) {
        List<CommentDto> comments = commentService.findCommentsByPurchaseId(purchase_id);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            @RequestBody CommentDto commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setId(id);
        commentService.updateComment(userId, commentDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentService.deleteComment(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}