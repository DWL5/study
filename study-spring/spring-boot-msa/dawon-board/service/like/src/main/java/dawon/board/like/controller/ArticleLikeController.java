package dawon.board.like.controller;

import dawon.board.like.service.ArticleLikeService;
import dawon.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/article-likes/articles")
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    @GetMapping("/{articleId}/users/{userId}")
    public ArticleLikeResponse read(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId) {

        return articleLikeService.read(articleId, userId);
    }

    @PostMapping("/{articleId}/users/{userId}")
    public void like(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId) {
        System.out.println("hello");
        articleLikeService.like(articleId, userId);
    }

    @DeleteMapping("/{articleId}/users/{userId}")
    public void unlike(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId) {

        articleLikeService.unlike(articleId, userId);
    }
}
