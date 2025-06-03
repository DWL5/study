package dawon.board.hotarticle.consumer;

import dawon.board.common.event.Event;
import dawon.board.common.event.EventPayload;
import dawon.board.common.event.EventType;
import dawon.board.hotarticle.service.HotArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotArticleEventConsumer {
    private final HotArticleService hotArticleService;

    @KafkaListener(topics = {
            EventType.Topic.DAWON_BOARD_ARTICLE,
            EventType.Topic.DAWON_BOARD_COMMENT,
            EventType.Topic.DAWON_BOARD_LIKE,
            EventType.Topic.DAWON_BOARD_VIEW
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[HotArticleEventConsumer.listen] receive message = {}", message);
        Event<EventPayload> event = Event.fromJson(message);

        if (event != null) {
            hotArticleService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
