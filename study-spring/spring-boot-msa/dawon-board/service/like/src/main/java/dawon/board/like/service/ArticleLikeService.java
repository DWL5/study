package dawon.board.like.service;

import dawon.board.common.dataserializer.Snowflake;
import dawon.board.like.entity.ArticleLike;
import dawon.board.like.repository.ArticleLikeRepository;
import dawon.board.like.service.response.ArticleLikeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleLikeRepository articleLikeRepository;

    public ArticleLikeResponse read(Long articleId, Long userId) {
        return articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .map(ArticleLikeResponse::from)
                .orElseThrow();
    }

    @Transactional
    public void like(Long articleId, Long userId) {
        System.out.println("like called.");
        articleLikeRepository.save(
                ArticleLike.create(snowflake.nextId(),
                        articleId,
                        userId)
        );
    }

    @Transactional
    public void unlike(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .ifPresent(articleLikeRepository::delete);
    }
}
