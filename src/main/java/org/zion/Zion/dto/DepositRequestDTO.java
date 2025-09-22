package org.zion.Zion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DepositRequestDTO {
    
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Deposit must be at least 1 unit")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
