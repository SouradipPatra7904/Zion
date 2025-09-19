package org.zion.Zion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zion.Zion.event.DepositMadeEvent;
import org.zion.Zion.services.EventPublisher;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/accounts")
public class DepositController {

    private final EventPublisher eventPublisher;

    public DepositController(EventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("{account}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountId, @RequestBody Map<String, Object> body) throws Exception{
        double amount = ((Number) body.get("amount")).doubleValue();

        DepositMadeEvent dep_Event = new DepositMadeEvent(accountId, amount, Instant.now() );

        eventPublisher.publish("account-events", dep_Event);

        return ResponseEntity.accepted().body(Map.of("status", "accepted", "accountId", accountId, "amount", amount));
    }
    
    
}
