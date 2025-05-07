package dawon.board.article.service.response;

import dawon.board.article.entity.Article;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long articleId,
        String title,
        String content,
        Long boardId,
        Long writerId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getBoardId(),
                article.getWriterId(),
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
