package com.arihant.expense_tracker.controller;

import com.arihant.expense_tracker.dto.ConversionRateRequestDto;
import com.arihant.expense_tracker.dto.ConversionRateResponseDto;
import com.arihant.expense_tracker.service.MarketDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market")
public class MarketDataController {

    private MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/currency")
    public ConversionRateResponseDto convertCurrency(@RequestBody ConversionRateRequestDto dto){
        return marketDataService.fetchCurrencyExchange(dto);
    }
}
