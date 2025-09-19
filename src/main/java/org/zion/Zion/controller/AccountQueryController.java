package org.zion.Zion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zion.Zion.query.AccountView;
import org.zion.Zion.query.AccountViewRepository;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountQueryController {

    private final AccountViewRepository repo;

    public AccountQueryController(AccountViewRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable String accountId) {
        Optional<AccountView> accountOpt = repo.findById(accountId);
        return accountOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

