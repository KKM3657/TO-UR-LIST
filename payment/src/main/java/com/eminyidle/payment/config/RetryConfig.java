package com.eminyidle.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RetryConfig {
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 재시도할 예외를 설정합니다.
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(SQLException.class, true);  // SQLException 예외를 재시도 대상에 추가합니다.
        // Retry policy: maximum 3 attempts
        RetryPolicy retryPolicy = new SimpleRetryPolicy(4, retryableExceptions); // 최대 시도 횟수 4번, 1번의 시행과 3번의 재시도
        retryTemplate.setRetryPolicy(retryPolicy);

        // Backoff policy: fixed delay between retries
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000); // 2 seconds
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
