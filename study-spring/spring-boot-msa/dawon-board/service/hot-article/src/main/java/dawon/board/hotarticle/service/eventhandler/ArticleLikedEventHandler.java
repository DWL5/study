package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventType;
import dawon.board.common.event.payload.ArticleLikedEventPayload;
import dawon.board.hotarticle.repository.ArticleLikeCountRepository;
import dawon.board.hotarticle.utils.TimeCalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler<ArticleLikedEventPayload> {
    private final ArticleLikeCountRepository articleLikeCountRepository;


    @Override
    public void handle(Event<ArticleLikedEventPayload> event) {
        ArticleLikedEventPayload payload = event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculationUtils.calculateDurationMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleLikedEventPayload> event) {
        return EventType.ARTICLE_LIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleLikedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
