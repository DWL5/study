package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventType;
import dawon.board.common.event.payload.ArticleUnlikedEventPayload;
import dawon.board.common.event.payload.ArticleViewedEventPayload;
import dawon.board.hotarticle.repository.ArticleLikeCountRepository;
import dawon.board.hotarticle.repository.ArticleViewCountRepository;
import dawon.board.hotarticle.utils.TimeCalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewEventHandler implements EventHandler<ArticleViewedEventPayload> {
    private final ArticleViewCountRepository articleViewCountRepository;

    @Override
    public void handle(Event<ArticleViewedEventPayload> event) {
        ArticleViewedEventPayload payload = event.getPayload();
        articleViewCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleViewCount(),
                TimeCalculationUtils.calculateDurationMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleViewedEventPayload> event) {
        return EventType.ARTICLE_VIEWED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleViewedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
