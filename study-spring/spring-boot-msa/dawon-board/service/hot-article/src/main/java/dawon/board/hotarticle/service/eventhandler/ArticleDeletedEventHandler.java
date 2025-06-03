package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventType;
import dawon.board.common.event.payload.ArticleCreatedEventPayload;
import dawon.board.common.event.payload.ArticleDeletedEventPayload;
import dawon.board.hotarticle.repository.ArticleCreatedTimeRepository;
import dawon.board.hotarticle.repository.HotArticleListRepository;
import dawon.board.hotarticle.utils.TimeCalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
    private final HotArticleListRepository hotArticleListRepository;
    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleCreatedTimeRepository.delete(payload.getArticleId());
        hotArticleListRepository.remove(payload.getArticleId(), payload.getCreatedAt());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleDeletedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
