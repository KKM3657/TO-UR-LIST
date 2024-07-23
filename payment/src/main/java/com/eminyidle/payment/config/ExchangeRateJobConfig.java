package com.eminyidle.payment.config;

import com.eminyidle.payment.batch.ExchangeRateTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExchangeRateJobConfig {
    private final ExchangeRateTasklet exchangeRateTasklet;

    @Bean
    public Job exchangeRateJob(JobRepository jobRepository, Step getExchangeRateStep) {
        return new JobBuilder("exchangeRateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(getExchangeRateStep)
                .build();
    }
    // 테스트 용
//    @Bean
//    public Job exchangeRateJob(JobRepository jobRepository, Step getExchangeRateStep) {
//        return new JobBuilder("exchangeRateJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(getExchangeRateStep)
//                .on("FAILED").stop() //실패할 경우 멈추기
//                .end()
//                .build();
//    }
//    // Chunk 방식
//    @Bean
//    public Step myFirstStep(JobRepository jobRepository){
//        return new StepBuilder("myFirstStep",jobRepository)
//                .<String, String>chunk(1000,transactionManager)
//                .reader(itemReader())
//                .writer(itemWriter())
//                .build();
//    }

    // Tasklet 방식
    @Bean
    public Step getExchangeRateStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("getExchangeRateStep", jobRepository)
                .tasklet(exchangeRateTasklet, platformTransactionManager).build();
    }

//    public Tasklet testTasklet(){
//        return ((contribution, chunkContext) -> {
//            System.out.println("***** hello batch! *****");
//            // 원하는 비지니스 로직 작성
//            return RepeatStatus.FINISHED;
//        });
//    }
}
