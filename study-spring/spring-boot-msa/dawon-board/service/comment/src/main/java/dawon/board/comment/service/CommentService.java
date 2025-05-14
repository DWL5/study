package dawon.board.comment.service;

import dawon.board.comment.entity.Comment;
import dawon.board.comment.repository.CommentRepository;
import dawon.board.comment.service.request.CommentCreateRequest;
import dawon.board.comment.service.response.CommentPageResponse;
import dawon.board.comment.service.response.CommentResponse;
import dawon.board.common.snowflake.Snowflake;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final Snowflake snowflake = new Snowflake();
    private final CommentRepository commentRepository;


    @Transactional
    public CommentResponse create(CommentCreateRequest request) {
        Comment parent = findParent(request);
        Comment comment = commentRepository.save(
                Comment.create(snowflake.nextId(),
                        request.content(),
                        parent == null ? null : parent.getParentCommentId(),
                        request.articleId(),
                        request.writerId())
        );

        return CommentResponse.from(comment);
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.parentCommentId();
        if (parentCommentId == null) {
            return null;
        }

        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::isDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }

    public CommentResponse read(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        return CommentResponse.from(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::isDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::isDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

    public CommentPageResponse readAll(Long articleId, Long page, Long pageSize) {
        return CommentPageResponse.of(
                commentRepository.findAll(articleId, (page - 1) * pageSize, pageSize).stream()
                        .map(CommentResponse::from)
                        .toList(),
                commentRepository.count(articleId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<CommentResponse> readAll(Long articleId, Long lastParentCommentId, Long lastCommentId, Long limit) {
        List<Comment> comments = lastParentCommentId == null || lastCommentId == null ?
                commentRepository.findAllInfiniteScroll(articleId, limit) :
                commentRepository.findAllInfiniteScroll(articleId, lastParentCommentId, lastCommentId, limit);
        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }
}
