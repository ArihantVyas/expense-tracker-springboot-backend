package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.ConversionRateRequestDto;
import com.arihant.expense_tracker.dto.ConversionRateResponseDto;
import com.arihant.expense_tracker.dto.ConversionRatesFetchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarketDataService {

    private RestTemplate restTemplate;

    @Value("${EXCHANGE_CURRENCY_API_KEY}")
    private String EXCHANGE_CURRENCY_API_KEY;

    String exchangeUrl = "https://v6.exchangerate-api.com/v6/EXCHANGE_API_KEY/latest/BASE_CURR";

    public MarketDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConversionRateResponseDto fetchCurrencyExchange(ConversionRateRequestDto dto){

        String baseCurr = dto.getBaseCurr();
        String toCurr = dto.getToCurr();
        Double convertedValue;


        String finalExchangeUrl = exchangeUrl.replace("EXCHANGE_API_KEY",EXCHANGE_CURRENCY_API_KEY).replace("BASE_CURR",baseCurr);
        ResponseEntity<ConversionRatesFetchResponse> response = restTemplate.exchange(finalExchangeUrl, HttpMethod.GET,null, ConversionRatesFetchResponse.class);

        convertedValue = response.getBody().getConversionRates().get(toCurr);

        return new ConversionRateResponseDto(baseCurr,toCurr,convertedValue);

    }
}
