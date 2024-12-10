package com.example.springbatchbasic.job.validatedparam;

import com.example.springbatchbasic.job.validatedparam.validator.FileParamValidator;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidatedParamJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job validatedParamJob(Step validatedParamStep) {
        return jobBuilderFactory.get("validatedParamJob")
                .incrementer(new RunIdIncrementer())
                .validator(getMultipleValidator())
                .start(validatedParamStep)
                .build();
    }

    private CompositeJobParametersValidator getMultipleValidator() {
        CompositeJobParametersValidator validators = new CompositeJobParametersValidator();
        validators.setValidators(Arrays.asList(new FileParamValidator()));

        return validators;
    }
    @JobScope
    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet) {
        return stepBuilderFactory.get("helloWorldStep")
                .tasklet(validatedParamTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return (contribution, chunkContext) -> {
            System.out.println(fileName);
            System.out.println("validatedParamTasklet");
            return RepeatStatus.FINISHED;
        };
    }
}
