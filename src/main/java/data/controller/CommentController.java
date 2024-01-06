package data.controller;

import data.dto.ApiResponse;
import data.dto.CommentDto;
import data.service.CommentService;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final JwtProvider jwtProvider;

    @Autowired
    public CommentController(CommentService commentService, JwtProvider jwtProvider) {
        this.commentService = commentService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<?>> createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody CommentDto.Request commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setUserId(userId);
        commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created());
    }

    @GetMapping("/purchase/{purchase_id}/comments")
    public ResponseEntity<ApiResponse<List<CommentDto.Response>>> findCommentById(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable int purchase_id
    ) {
        int userId = tokenValidation(token);
        return ResponseEntity.ok(ApiResponse.ok(commentService.findCommentsByPurchaseId(purchase_id, userId)));
    }

    @PatchMapping("/comment/{id}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            @RequestBody CommentDto.Request commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setId(id);
        commentService.updateComment(userId, commentDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ApiResponse<?>> deleteComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentService.deleteComment(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }

    private int tokenValidation(String token) {
        if (token != null) {
            return jwtProvider.parseJwt(token);
        }
        return 0;
    }
}
