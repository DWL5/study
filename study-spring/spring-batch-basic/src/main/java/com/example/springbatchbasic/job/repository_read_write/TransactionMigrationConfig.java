package com.example.springbatchbasic.job.repository_read_write;

import com.example.springbatchbasic.job.repository_read_write.domain.account.AccountEntity;
import com.example.springbatchbasic.job.repository_read_write.domain.account.repository.AccountRepository;
import com.example.springbatchbasic.job.repository_read_write.domain.order.OrderEntity;
import com.example.springbatchbasic.job.repository_read_write.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class TransactionMigrationConfig {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job trMigrationJob(Step trMigrationStep) {
        return jobBuilderFactory.get("trMigrationJob")
                .incrementer(new RunIdIncrementer())
                .start(trMigrationStep)
                .build();
    }

    @JobScope
    @Bean
    public Step trMigrationStep(ItemReader trOrderReader, ItemProcessor trOrderProcessor, ItemWriter trOrderItemWriter) {
        return stepBuilderFactory.get("trMigrationStep")
                .<OrderEntity, AccountEntity>chunk(5)
                .reader(trOrderReader)
                .processor(trOrderProcessor)
                .writer(trOrderItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<OrderEntity, AccountEntity> trOrderProcessor() {
        return AccountEntity::of;
    }


    @StepScope
    @Bean
    public RepositoryItemReader<OrderEntity> trOrderReader() {
        return new RepositoryItemReaderBuilder<OrderEntity>()
                .name("trOrderReader")
                .repository(orderRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<AccountEntity> trOrderRepositoryItemWriter() {
        return new RepositoryItemWriterBuilder<AccountEntity>()
                .repository(accountRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<AccountEntity> trOrderItemWriter() {
        return items -> items.forEach(item -> accountRepository.save(item));
    }
}
