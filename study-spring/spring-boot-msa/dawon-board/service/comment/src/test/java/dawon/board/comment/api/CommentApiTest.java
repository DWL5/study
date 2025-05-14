package dawon.board.comment.api;

import dawon.board.comment.service.response.CommentPageResponse;
import dawon.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.commentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.commentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.commentId()));
        System.out.println("\tcommentId=%s".formatted(response2.commentId()));
        System.out.println("\tcommentId=%s".formatted(response3.commentId()));

        /**
        parent -> commentId=179961364388167680
        commentId=179961364841152512
        commentId=179961364887289856
         **/
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 179961364388167680L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        restClient.delete()
                .uri("/v1/comments/{commentId}", 179961364887289856L)
                .retrieve();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.commentCount());
        for (CommentResponse comment : response.comments()) {
            if (!comment.commentId().equals(comment.parentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.commentId());
        }

        /**
         * 1번 페이지 수행 결과
         * comment.getCommentId() = 179965031515381760
         * 	comment.getCommentId() = 179965031800594432
         * 	comment.getCommentId() = 179965031850926080
         * comment.getCommentId() = 179965469389418496
         * 	comment.getCommentId() = 179965469435555886
         * comment.getCommentId() = 179965469389418497
         * 	comment.getCommentId() = 179965469435555884
         * comment.getCommentId() = 179965469389418498
         * 	comment.getCommentId() = 179965469435555877
         * comment.getCommentId() = 179965469389418499
         */
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> responses1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        System.out.println("firstPage");
        for (CommentResponse comment : responses1) {
            if (!comment.commentId().equals(comment.parentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.commentId());
        }

        Long lastParentCommentId = responses1.getLast().parentCommentId();
        Long lastCommentId = responses1.getLast().commentId();

        List<CommentResponse> responses2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("secondPage");
        for (CommentResponse comment : responses2) {
            if (!comment.commentId().equals(comment.parentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.commentId());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
