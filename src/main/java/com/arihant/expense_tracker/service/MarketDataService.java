package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.ConversionRateRequestDto;
import com.arihant.expense_tracker.dto.ConversionRateResponseDto;
import com.arihant.expense_tracker.dto.ConversionRatesFetchResponse;
import com.arihant.expense_tracker.enums.CacheStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarketDataService {

    @Value("${EXCHANGE_CURRENCY_API_KEY}")
    private String EXCHANGE_CURRENCY_API_KEY;

    private RestTemplate restTemplate;

    private LRUCachingService cache;

    String exchangeUrl = "https://v6.exchangerate-api.com/v6/EXCHANGE_API_KEY/latest/BASE_CURR";

    public MarketDataService(RestTemplate restTemplate, LRUCachingService cache) {
        this.restTemplate = restTemplate;
        this.cache = cache;
    }

    public ConversionRateResponseDto fetchCurrencyExchange(ConversionRateRequestDto dto){

        String baseCurr = dto.getBaseCurr();
        String toCurr = dto.getToCurr();
        Double convertedValue;

        if(cache.cacheStatus(dto) == CacheStatus.CACHE_HIT){
            convertedValue = cache.getFromCache(dto);
            return new ConversionRateResponseDto(baseCurr,toCurr,convertedValue);
        } else if(cache.cacheStatus(dto) == CacheStatus.CACHE_MISS) {

            String finalExchangeUrl = exchangeUrl.replace("EXCHANGE_API_KEY",EXCHANGE_CURRENCY_API_KEY).replace("BASE_CURR",baseCurr);
            ResponseEntity<ConversionRatesFetchResponse> response = restTemplate.exchange(finalExchangeUrl, HttpMethod.GET,null, ConversionRatesFetchResponse.class);

            convertedValue = response.getBody().getConversionRates().get(toCurr);

            cache.storeInCache(dto,convertedValue);

            return new ConversionRateResponseDto(baseCurr,toCurr,convertedValue);
        }
        return null;
    }
}
