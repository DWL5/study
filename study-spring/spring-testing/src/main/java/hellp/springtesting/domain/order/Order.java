package hellp.springtesting.domain.order;

import hellp.springtesting.domain.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Order {

    private final List<Beverage> beverages;
    private final LocalDateTime orderDateTime;
}
