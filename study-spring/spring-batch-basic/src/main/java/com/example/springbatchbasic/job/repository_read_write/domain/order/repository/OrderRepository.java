package com.example.springbatchbasic.job.repository_read_write.domain.order.repository;

import com.example.springbatchbasic.job.repository_read_write.domain.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
