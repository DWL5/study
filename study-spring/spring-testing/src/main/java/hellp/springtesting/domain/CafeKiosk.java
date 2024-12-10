package hellp.springtesting.domain;

import hellp.springtesting.domain.beverage.Americano;
import hellp.springtesting.domain.beverage.Beverage;
import hellp.springtesting.domain.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {
    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add (Beverage beverage) {
        add(beverage, 1);
    }

    public void add (Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();;
    }

    public int calculateTotalPrice() {
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder(LocalDateTime orderDateTime) {
        LocalTime currentTime = orderDateTime.toLocalTime();

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(beverages, LocalDateTime.now());
    }
}
