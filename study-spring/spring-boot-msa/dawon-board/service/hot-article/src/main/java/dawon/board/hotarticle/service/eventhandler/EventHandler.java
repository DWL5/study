package dawon.board.hotarticle.service.eventhandler;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
    Long findArticleId(Event<T> event);
}
