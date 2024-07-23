package com.eminyidle.payment.exhandler;

import com.eminyidle.payment.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class PaymentExceptionHandler {

    private void makeErrorMessage(StringBuilder errorMessage, Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();

        if (stackTrace.length > 0) {
            StackTraceElement topFrame = stackTrace[0];
            String className = topFrame.getClassName();
            String methodName = topFrame.getMethodName();

            errorMessage.append(className).append(".").append(methodName).append(": ");
        }
    }

    @ExceptionHandler(CurrencyNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> currencyNotExistsExceptionHandler(
            CurrencyNotExistException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);

        errorMessage.append("존재하지 않은 통화코드 입니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ExchangeRateNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> exchangeRateNotExistsExceptionHandler(
            ExchangeRateNotExistException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);

        errorMessage.append("존재하지 않은 환율정보 입니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PaymentNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> paymentNotExistsExceptionHandler(
            PaymentNotExistException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);

        errorMessage.append("존재하지 않은 지출정보 입니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserIdNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> userIdNotExistsExceptionHandler(
            UserIdNotExistException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);

        errorMessage.append("유저 ID가 없습니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(KafkaDataNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> kafkaDataNotExistsExceptionHandler(
            KafkaDataNotExistException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);
        errorMessage.append("유저 ID가 없습니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DuplicateExchangeRateDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> duplicateExchangeRateDataExceptionHandler(
            DuplicateExchangeRateDataException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);
        errorMessage.append("이미 환율 데이터가 반영되어 있습니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(FailSaveExchangeRateDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> failExchangeRateDataExceptionHandler(
            FailSaveExchangeRateDataException e) {
        StringBuilder errorMessage = new StringBuilder();

        makeErrorMessage(errorMessage, e);
        errorMessage.append("이미 환율 데이터가 반영되어 있습니다.");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

//    @ExceptionHandler(MissingRequestHeaderException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ResponseEntity<String> userIdNotExistsExceptionHandler(
//            MissingRequestHeaderException e) {
//        StringBuilder errorMessage = new StringBuilder();
//
//        makeErrorMessage(errorMessage, e);
//
//        errorMessage.append("유저 ID가 없습니다.");
//        return ResponseEntity.badRequest().body(errorMessage.toString());
//    }

}
