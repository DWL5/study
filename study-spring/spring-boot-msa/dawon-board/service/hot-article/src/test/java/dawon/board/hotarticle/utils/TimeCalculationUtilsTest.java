package dawon.board.hotarticle.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;

class TimeCalculationUtilsTest {

    @Test
    void test() {
        Duration duration = TimeCalculationUtils.calculateDurationMidnight();
        System.out.println("duration.getSeconds() / 60: " + duration.getSeconds() / 60);
    }

}