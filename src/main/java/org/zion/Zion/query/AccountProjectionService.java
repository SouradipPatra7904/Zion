package org.zion.Zion.query;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AccountProjectionService {
    private final AccountViewRepository repo;

    public AccountProjectionService(AccountViewRepository repo){
        this.repo = repo;
    }

    @Transactional
    public void applyDeposit(String accountId, double amount){
        var opt = repo.findById(accountId);
        var view = opt.orElseGet(() -> {
            var a = new AccountView(accountId, BigDecimal.ZERO);
            return a;
        });

        view.setBalance(view.getBalance().add(BigDecimal.valueOf(amount)));
        repo.save(view);
        
    }
}
