package com.arihant.expense_tracker.dto;

import java.util.Objects;

public class ConversionRateRequestDto {
    private String baseCurr;
    private String toCurr;

    public ConversionRateRequestDto() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurr,toCurr);
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) return true;

        // returns false if the obj is null,so no need of explicit checking for null
        if(!(obj instanceof ConversionRateRequestDto)) return false;

        ConversionRateRequestDto other = (ConversionRateRequestDto) obj;

        // if((this.baseCurr).equals(other.baseCurr) && (this.toCurr).equals(other.toCurr)) return true;

        if(Objects.equals(this.baseCurr, other.baseCurr) && Objects.equals(this.toCurr, other.toCurr)) return true;

        return false;
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
