package hello.springcore.discount;

import hello.springcore.member.Member;

public interface DiscountPolicy {

    /**
     * @return 대상할인금액
     */
    int discount(Member member, int price);
}
