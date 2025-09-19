package org.zion.Zion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.zion.Zion.query.AccountView;
import org.zion.Zion.services.EventPublisher;

public class AccountController {
    private final EventPublisher eventPublisher;

    public AccountController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountView account) {
        // Save to DB (via JPA repo)
        // Publish Kafka event
        eventPublisher.publish("account-events", "Created account: " + account.getAccountId());
        return ResponseEntity.ok("Account created & event published");
    }
}
