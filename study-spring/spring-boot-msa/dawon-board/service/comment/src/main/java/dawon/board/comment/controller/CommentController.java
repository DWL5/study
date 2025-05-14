package dawon.board.comment.controller;

import dawon.board.comment.service.CommentService;
import dawon.board.comment.service.request.CommentCreateRequest;
import dawon.board.comment.service.response.CommentPageResponse;
import dawon.board.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentResponse read(@PathVariable Long commentId) {
        return commentService.read(commentId);
    }

    @PostMapping
    public CommentResponse create(@RequestBody CommentCreateRequest request) {
        return commentService.create(request);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

    @GetMapping
    public CommentPageResponse readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(articleId, page, pageSize);
    }

    @GetMapping("/infinite-scroll")
    public List<CommentResponse> readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "lastParentCommentId", required = false) Long lastParentCommentId,
            @RequestParam(value = "lastCommentId", required = false) Long lastCommentId,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(articleId, lastParentCommentId, lastCommentId, pageSize);
    }
}
