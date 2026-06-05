package com.arihant.expense_tracker.dto;

public class ConversionRateResponseDto {
    String baseCurr;
    String toCurr;
    Double convertedValue;

    public ConversionRateResponseDto() {
    }

    public ConversionRateResponseDto(String baseCurr, String toCurr, Double convertedValue) {
        this.baseCurr = baseCurr;
        this.toCurr = toCurr;
        this.convertedValue = convertedValue;
    }

    public String getBaseCurr() {
        return baseCurr;
    }

    public void setBaseCurr(String baseCurr) {
        this.baseCurr = baseCurr;
    }

    public String getToCurr() {
        return toCurr;
    }

    public void setToCurr(String toCurr) {
        this.toCurr = toCurr;
    }

    public Double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(Double convertedValue) {
        this.convertedValue = convertedValue;
    }
}
