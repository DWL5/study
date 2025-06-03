package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventType;
import dawon.board.common.event.payload.CommentCreatedEventPayload;
import dawon.board.hotarticle.repository.ArticleCommentCountRepository;
import dawon.board.hotarticle.utils.TimeCalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload> {
    private final ArticleCommentCountRepository articleCommentCountRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        CommentCreatedEventPayload payload = event.getPayload();
        articleCommentCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleCommentCount(),
                TimeCalculationUtils.calculateDurationMidnight()
        );

    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }

    @Override
    public Long findArticleId(Event<CommentCreatedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
