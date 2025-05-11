package dawon.board.comment.service.response;

import dawon.board.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long commentId,
        String content,
        Long parentCommentId,
        Long articleId,
        Long writerId,
        boolean deleted,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .parentCommentId(comment.getParentCommentId())
                .articleId(comment.getArticleId())
                .writerId(comment.getWriterId())
                .deleted(comment.isDeleted())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
