package data.service;

import data.dto.CommentDto;
import data.exception.BusinessException;
import data.exception.UnauthorizedException;
import data.mapper.CommentMapper;
import data.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public void createComment(CommentDto commentDto){
        validateComment(commentDto);
        commentMapper.createComment(commentDto);
    }

    public List<CommentDto> findCommentsByPurchaseId(int purchaseId) {
        List<CommentDto> allComments = commentMapper.findCommentsByPurchaseId(purchaseId);
        return organizeComments(allComments);
    }

    public void updateComment(int loginId, CommentDto commentDto) {
        CommentDto existingComment = findExistingComment(commentDto.getId());
        validateUserPermission(loginId, existingComment.getUserId());
        validateComment(commentDto);
        validateIsDeleted(existingComment.isDelete());
        commentMapper.updateComment(commentDto);
    }

    public void deleteComment(int loginId, int id){
        CommentDto existingComment = findExistingComment(id);
        validateUserPermission(loginId, existingComment.getUserId());
        validateIsDeleted(existingComment.isDelete());
        commentMapper.deleteComment(id);
    }

    private void validateComment(CommentDto commentDto) {
        if (commentDto == null || commentDto.getContent() == null || commentDto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
    }

    private CommentDto findExistingComment(int commentId) {
        CommentDto existingComment = commentMapper.findCommentById(commentId);
        if (existingComment == null) {
            throw new NotFoundException("Comment not found");
        }
        return existingComment;
    }

    private void validateUserPermission(int loginId, int userId) {
        if (loginId != userId) {
            throw new UnauthorizedException("Edit permission denied");
        }
    }

    private void validateIsDeleted(boolean isDeleted) {
        if (isDeleted) {
            throw new BusinessException("Cannot modify a deleted comment");
        }
    }

    private List<CommentDto> organizeComments(List<CommentDto> allComments) {
        Map<Integer, CommentDto> commentMap = new HashMap<>();
        List<CommentDto> topLevelComments = new ArrayList<>();

        for (CommentDto comment : allComments) {
            commentMap.put(comment.getId(), comment);

            if(comment.getParentId() == 0) {
                topLevelComments.add(comment);
            }
        }

        for (CommentDto comment : allComments) {
            if (comment.getParentId() != 0) {
                CommentDto parentComment = commentMap.get(comment.getParentId());
                if (parentComment != null) {
                    if (parentComment.getReplies() == null) {
                        parentComment.setReplies(new ArrayList<>());
                    }
                    parentComment.getReplies().add(comment);
                }
            }
        }
        return topLevelComments;
    }
}