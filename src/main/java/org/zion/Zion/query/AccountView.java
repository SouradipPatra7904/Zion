package org.zion.Zion.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class AccountView {
    @Id
    private String accountId;
    private BigDecimal balance;
    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountView(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }
}
