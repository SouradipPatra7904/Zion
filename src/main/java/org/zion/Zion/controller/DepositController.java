package org.zion.Zion.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zion.Zion.dto.DepositRequestDTO;
import org.zion.Zion.event.DepositMadeEvent;
import org.zion.Zion.services.EventPublisher;

import jakarta.validation.Valid;
import java.time.Instant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/accounts")
public class DepositController {

    private final EventPublisher eventPublisher;

    public DepositController(EventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("{accountId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountId, @Valid @RequestBody DepositRequestDTO request) throws Exception {
        double amount = request.getAmount();
        DepositMadeEvent depEvent = new DepositMadeEvent(accountId, amount, Instant.now());

        eventPublisher.publish("account-events", depEvent);

        EntityModel<DepositMadeEvent> model = EntityModel.of(depEvent,
                linkTo(methodOn(DepositController.class).deposit(accountId, request)).withSelfRel(),
                linkTo(methodOn(AccountQueryController.class).getAccount(accountId)).withRel("account"));

        return ResponseEntity.accepted().body(model);
    }
}
