package dawon.board.article.service;

import dawon.board.article.entity.Article;
import dawon.board.article.repository.ArticleRepository;
import dawon.board.article.service.request.ArticleCreateRequest;
import dawon.board.article.service.request.ArticleUpdateRequest;
import dawon.board.article.service.response.ArticlePageResponse;
import dawon.board.article.service.response.ArticleResponse;
import jakarta.transaction.Transactional;
import dawon.board.common.dataserializer.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.title(), request.content(), request.boardId(), request.writerId()));

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.title(), request.content());
        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(boardId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);

        return articles.stream().map(ArticleResponse::from).toList();

    }
}
