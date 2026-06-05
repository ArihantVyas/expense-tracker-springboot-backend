package com.arihant.expense_tracker.dto;

public class ConversionRateRequestDto {
    String baseCurr;
    String toCurr;

    public ConversionRateRequestDto() {
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
}
