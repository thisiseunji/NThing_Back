package data.mapper;

import data.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int createComment(CommentDto.Request comment);
    List<CommentDto.Response> findCommentsByPurchaseId(int purchaseId);
    List<CommentDto.Comment> findRepliesByParentId(int parentId);
    CommentDto.Comment findCommentById(int id);
    void updateComment(CommentDto.Request comment);
    void deleteComment(int id);
}
