package com.arihant.expense_tracker.dto;

import com.arihant.expense_tracker.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ExpenseRequestDto {

    private Long expId;

    @NotBlank(message = "Title name must be provided")
    @Size(max = 60)
    private String title;

    // Will only accept these listed values. Requires validation handling in controller
    @Pattern(regexp = "Food|Travel|Shopping|Entertainment|Work|Rent|Gym|Bills|Subscriptions")
    private String category;

    // It will also only accept the values defined in enum. Also requires validation handling in controller
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private LocalDate expenseDate;

    @Size(max = 500)
    private String remarks;

    // private LocalDateTime entryDateTime;

    public ExpenseRequestDto() {
    }

    public Long getExpId() {
        return expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
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
        return remarks;
    }

    public void setRemark(String remarks) {
        this.remarks = remarks;
    }

    /*
    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }
    */
}
