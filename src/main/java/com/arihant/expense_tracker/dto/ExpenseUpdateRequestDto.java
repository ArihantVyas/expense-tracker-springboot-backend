package com.arihant.expense_tracker.dto;

import com.arihant.expense_tracker.enums.TransactionType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ExpenseUpdateRequestDto {

    @Size(max = 60)
    private String title;

    @Pattern(regexp = "Food|Travel|Shopping|Entertainment|Work|Rent|Gym|Bills|Subscriptions")
    private String category;

    private TransactionType type;

    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private LocalDate expenseDate;

    @Size(max = 500)
    private String remark;

    public ExpenseUpdateRequestDto() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
