package hellp.springtesting.domain;

import hellp.springtesting.domain.beverage.Americano;
import hellp.springtesting.domain.beverage.Latte;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(), 1);
        cafeKiosk.add(new Latte(), 1);

    }
}
