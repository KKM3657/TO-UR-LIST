package com.eminyidle.payment.service;

import com.eminyidle.payment.dto.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public interface BatchService {
    void saveExchangeRates(String responseBody) throws SQLException;

    List<ExchangeRate> loadExchangeRateList();
}
