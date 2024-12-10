package com.example.springbatchbasic.job.jobListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobListenerConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobListenerJob() {
        return jobBuilderFactory.get("jobListenerJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(jobListenerStep())
                .build();
    }

    @JobScope
    @Bean
    public Step jobListenerStep() {
        return stepBuilderFactory.get("jobListenerStep")
                .tasklet(jobListenerTasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet jobListenerTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("jobListenerTasklet");
            return RepeatStatus.FINISHED;
        };
    }
}
