package hello.springcore.order;

import hello.springcore.discount.DiscountPolicy;
import hello.springcore.discount.FixDiscountPolicy;
import hello.springcore.member.Member;
import hello.springcore.member.MemberRepository;
import hello.springcore.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
