package dawon.board.hotarticle.service;

import dawon.board.common.event.Event;
import dawon.board.hotarticle.repository.ArticleCreatedTimeRepository;
import dawon.board.hotarticle.repository.HotArticleListRepository;
import dawon.board.hotarticle.service.eventhandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotArticleScoreUpdaterTest {
    @InjectMocks
    HotArticleScoreUpdater hotArticleScoreUpdater;
    @Mock
    HotArticleListRepository hotArticleListRepository;
    @Mock
    HotArticleScoreCalculator hotArticleScoreCalculator;
    @Mock
    ArticleCreatedTimeRepository articleCreatedTimeRepository;

    @Test
    void updateIfArticleNotCreatedTodayTest() {
        //given
        Long articleId = 1L;
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);

        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        given(articleCreatedTimeRepository.read(articleId)).willReturn(createTime);

        // when
        hotArticleScoreUpdater.update(event, eventHandler);

        //then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleListRepository, never())
                .add(anyLong(), any(LocalDateTime.class), anyLong(), anyLong(), any(Duration.class));

    }

    @Test
    void updateIfArticleCreatedTodayTest() {
        //given
        Long articleId = 1L;
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);

        LocalDateTime createTime = LocalDateTime.now();
        given(articleCreatedTimeRepository.read(articleId)).willReturn(createTime);

        // when
        hotArticleScoreUpdater.update(event, eventHandler);

        //then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleListRepository, never())
                .add(anyLong(), any(LocalDateTime.class), anyLong(), anyLong(), any(Duration.class));

    }
}