package com.example.springbatchbasic.job.repository_read_write;

import com.example.springbatchbasic.SpringBatchTestConfig;
import com.example.springbatchbasic.job.repository_read_write.domain.account.repository.AccountRepository;
import com.example.springbatchbasic.job.repository_read_write.domain.order.OrderEntity;
import com.example.springbatchbasic.job.repository_read_write.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {SpringBatchTestConfig.class, TransactionMigrationConfig.class})
class TransactionMigrationConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clean() {
        orderRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test()
    public void success_no_data() throws Exception {
        //when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        //then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(0, accountRepository.count());
    }

    @Test()
    public void success_exist_data() throws Exception {
        //given
        OrderEntity orderEntity1 = OrderEntity.builder()
                .id(null)
                .orderItem("kakao gift")
                .price(15000)
                .orderDate(new Date())
                .build();

        OrderEntity orderEntity2 = OrderEntity.builder()
                .id(null)
                .orderItem("naver gift")
                .price(15000)
                .orderDate(new Date())
                .build();

        orderRepository.save(orderEntity1);
        orderRepository.save(orderEntity2);

        //when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        //then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(2, accountRepository.count());
    }
}