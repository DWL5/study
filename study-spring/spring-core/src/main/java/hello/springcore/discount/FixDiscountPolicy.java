package hello.springcore.discount;

import hello.springcore.member.Grade;
import hello.springcore.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }

        return 0;
    }
}
