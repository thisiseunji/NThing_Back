package data.mapper;

import data.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int createComment(CommentDto commentDto);
    List<CommentDto> findCommentsByPurchaseId(int purchase_id);
    List<CommentDto> findRepliesByParentId(int parentId);
    CommentDto findCommentById(int id);
    void updateComment(CommentDto commentDto);
    void deleteComment(int id);
}