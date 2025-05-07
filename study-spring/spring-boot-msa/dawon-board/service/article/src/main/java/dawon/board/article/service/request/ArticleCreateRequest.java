package dawon.board.article.service.request;

public record ArticleCreateRequest(
        String title,
        String content,
        Long writerId,
        Long boardId
) {
}
