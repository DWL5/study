package dawon.board.view.repository;

import dawon.board.view.entity.ArticleViewCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {

    @Autowired
    ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void updateViewCountTest() {
        //given
        articleViewCountBackUpRepository.save(
                ArticleViewCount.init(1L, 0L)
        );

        entityManager.flush();
        entityManager.clear();

        int result1 = articleViewCountBackUpRepository.updateViewCount(1L, 100L);
        int result2 = articleViewCountBackUpRepository.updateViewCount(1L, 300L);
        int result3 = articleViewCountBackUpRepository.updateViewCount(1L, 200L);

        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(1);
        Assertions.assertThat(result3).isEqualTo(0);

        var articleViewCount = articleViewCountBackUpRepository.findById(1L).get();
        Assertions.assertThat(articleViewCount.getViewCount()).isEqualTo(300L);
    }
}