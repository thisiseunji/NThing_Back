package data.controller;

import data.dto.ApiResponseEntity;
import data.dto.CommentDto;
import data.service.CommentService;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponseEntity<?> createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody CommentDto.Request commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setUserId(userId);
        commentService.createComment(commentDto);
        return ApiResponseEntity.created();
    }

    @GetMapping("/purchase/{purchase_id}/comments")
    public ApiResponseEntity<List<CommentDto.Response>> findCommentById(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable int purchase_id
    ) {
        int userId = 0;

        if (token!=null) {
            userId = jwtProvider.parseJwt(token);
        }
        List<CommentDto.Response> comments = commentService.findCommentsByPurchaseId(purchase_id, userId);
        return ApiResponseEntity.ok(comments);
    }

    @PatchMapping("/comment/{id}")
    public ApiResponseEntity<?> updateComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            @RequestBody CommentDto.Request commentDto
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentDto.setId(id);
        commentService.updateComment(userId, commentDto);
        return ApiResponseEntity.noContent();
    }

    @DeleteMapping("/comment/{id}")
    public ApiResponseEntity<?> deleteComment(
            @RequestHeader("Authorization") String token,
            @PathVariable int id
    ) {
        int userId = jwtProvider.parseJwt(token);
        commentService.deleteComment(userId, id);
        return ApiResponseEntity.noContent();
    }
}
