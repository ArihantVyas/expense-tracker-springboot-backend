package com.arihant.expense_tracker.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ConversionRatesFetchResponse {

    private String result;

    @JsonProperty("base_code")
    private String base_code;

    @JsonProperty("conversion_rates")
    private Map<String,Double> conversionRates;

    public ConversionRatesFetchResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBase_code() {
        return base_code;
    }

    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}


