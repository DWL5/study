package dawon.board.article.api;

import dawon.board.article.entity.Article;
import dawon.board.article.service.response.ArticlePageResponse;
import dawon.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest() {
        ArticleResponse response = read(178120582882848768L);
        System.out.println("response = " + response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        update(121530268440289280L);
        ArticleResponse response = read(121530268440289280L);
        System.out.println("response = " + response);
    }

    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my content 22"))
                .retrieve();
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 121530268440289280L)
                .retrieve();
    }

    @Test
    void readAllTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageResponse.class);
        System.out.println("response getArticleCont() = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article = " + article.articleId());
        }
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<ArticleResponse> response = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        System.out.println("firstPage");
        System.out.println("response getArticleCont() = " + response.size());
        for (ArticleResponse article : response) {
            System.out.println("article = " + article.articleId());
        }

        Long lastArticleId = response.getLast().articleId();
        List<ArticleResponse> response2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        System.out.println("secondPage");
        System.out.println("response getArticleCont() = " + response2.size());
        for (ArticleResponse article : response2) {
            System.out.println("article = " + article.articleId());
        }
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

}
