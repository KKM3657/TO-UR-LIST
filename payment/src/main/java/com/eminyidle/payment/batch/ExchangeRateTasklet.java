package com.eminyidle.payment.batch;

import com.eminyidle.payment.dto.ExchangeRate;
import com.eminyidle.payment.exception.DuplicateExchangeRateDataException;
import com.eminyidle.payment.exception.FailSaveExchangeRateDataException;
import com.eminyidle.payment.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateTasklet implements Tasklet {

    @Value("${EXCHANGERATE_KEY}")
    String authKey;

    private final BatchService batchService;
    private final RetryTemplate retryTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 만약 이미 데이터가 있다면 넘어감
//        List<ExchangeRate> DuplicateExchangeDateList = batchService.loadExchangeRateList();
//        if (!DuplicateExchangeDateList.isEmpty()) {
//            log.debug("금일 환율 데이터는 이미 반영되어 있습니다.");
//            return RepeatStatus.FINISHED;
//        }
        return retryTemplate.execute(new RetryCallback<RepeatStatus, Exception>() {
            @Override
            public RepeatStatus doWithRetry(RetryContext context) throws Exception {
                int retryCount = context.getRetryCount();

                List<ExchangeRate> duplicateExchangeDateList = batchService.loadExchangeRateList();
                if (!duplicateExchangeDateList.isEmpty()) {
                    log.error("재시도 횟수: {} - 금일 환율 데이터는 이미 반영되어 있습니다.", retryCount);
//                    return RepeatStatus.FINISHED;
                    throw new DuplicateExchangeRateDataException("금일 환율 데이터는 이미 반영되어 있습니다.");
                }

                try {
                    RestTemplate restTemplate = new RestTemplate();

                    HttpHeaders headers = new HttpHeaders();
                    MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8);
                    headers.setContentType(mediaType);

                    String url = "https://v6.exchangerate-api.com/v6/" + authKey + "/latest/KRW";

                    // 환율 요청
                    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                    // 응답 처리
                    String responseBody = response.getBody();
                    log.info("Response Body: {}", responseBody);

                    // 환율 정보 저장
                    batchService.saveExchangeRates(responseBody);
                }
                catch (SQLException e) {
                    log.error("SQL 오류 발생, 재시도합니다.", e);
                    // 3번의 에러가 발생시 알림 보내기 구현 예정
                    if (retryCount > 0 && retryCount % 2 == 0) {

                    }
                    throw new FailSaveExchangeRateDataException("환율 정보 저장 실패");  // 재시도 정책에 따라 SQLException을 다시 던집니다.
                }
                return RepeatStatus.FINISHED;
            }
        });

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8);
//        headers.setContentType(mediaType);
//
//        // 요청 매개변수 설정
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//
//        String url = "https://v6.exchangerate-api.com/v6/" + authKey + "/latest/KRW";
//        // 환율 요청
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        // 응답 처리
//        String responseBody = response.getBody();
//        log.info("Response Body: {}", responseBody);
//
//        // 환율 정보 저장
//        batchService.saveExchangeRates(responseBody);
//
//        return RepeatStatus.FINISHED;
    }
}
