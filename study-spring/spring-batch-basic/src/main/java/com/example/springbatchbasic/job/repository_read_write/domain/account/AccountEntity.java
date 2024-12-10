package com.example.springbatchbasic.job.repository_read_write.domain.account;

import com.example.springbatchbasic.job.repository_read_write.domain.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderItem;
    private Integer price;
    private Date orderDate;
    private Date accountDate;


    public static AccountEntity of(OrderEntity orderEntity) {
        return AccountEntity.builder()
                .orderItem(orderEntity.getOrderItem())
                .price(orderEntity.getPrice())
                .orderDate(orderEntity.getOrderDate())
                .accountDate(new Date())
                .build();
    }
}
