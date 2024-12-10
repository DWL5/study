package hellp.springtesting.domain;

import hellp.springtesting.domain.beverage.Americano;
import hellp.springtesting.domain.beverage.Latte;
import hellp.springtesting.domain.order.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(), 1);

        System.out.println(">>>> 담긴 음료수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>>> 담긴 음료수 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @Test
    void addSeveralBeveragesZero() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano,0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void remove() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();

        Americano americano = new Americano();
        cafeKiosk.add(americano, 1);

        //when
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        cafeKiosk.remove(americano);

        //then
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();

        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano, 1);
        cafeKiosk.add(latte, 1);

        //when
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        cafeKiosk.clear();

        //then
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void calculateTotalPrice() {
    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 1);
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 21, 10, 0));

        //then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    @Test
    void createOrderNotOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 1);
        //then
        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 2, 21, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");
    }
}

