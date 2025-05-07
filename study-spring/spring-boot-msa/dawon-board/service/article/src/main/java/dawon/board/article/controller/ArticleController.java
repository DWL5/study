package dawon.board.article.controller;

import dawon.board.article.service.ArticleService;
import dawon.board.article.service.request.ArticleCreateRequest;
import dawon.board.article.service.request.ArticleUpdateRequest;
import dawon.board.article.service.response.ArticlePageResponse;
import dawon.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public ArticlePageResponse readAll(@RequestParam Long boardId, @RequestParam Long page, @RequestParam Long pageSize) {
        return articleService.readAll(boardId, page, pageSize);
    }

    @GetMapping("/infinite-scroll")
    public List<ArticleResponse> readInfiniteScroll(@RequestParam Long boardId, @RequestParam(required = false) Long lastArticleId,
                                                    @RequestParam Long pageSize) {
        return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
    }

    @GetMapping("/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @PostMapping
    public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
        return articleService.create(request);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse create(@PathVariable Long articleId, @RequestBody ArticleUpdateRequest request) {
        return articleService.update(articleId, request);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }
}
