package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventType;
import dawon.board.common.event.payload.ArticleLikedEventPayload;
import dawon.board.common.event.payload.ArticleUnlikedEventPayload;
import dawon.board.hotarticle.repository.ArticleLikeCountRepository;
import dawon.board.hotarticle.utils.TimeCalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleUnLikedEventHandler implements EventHandler<ArticleUnlikedEventPayload> {
    private final ArticleLikeCountRepository articleLikeCountRepository;


    @Override
    public void handle(Event<ArticleUnlikedEventPayload> event) {
        ArticleUnlikedEventPayload payload = event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculationUtils.calculateDurationMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleUnlikedEventPayload> event) {
        return EventType.ARTICLE_UNLIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleUnlikedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
