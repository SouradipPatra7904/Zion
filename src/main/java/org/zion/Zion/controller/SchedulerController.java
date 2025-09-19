package org.zion.Zion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.zion.Zion.services.EventPublisher;

public class SchedulerController {
    private final EventPublisher eventPublisher;

    public SchedulerController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleEvent(@RequestBody String event) {
        eventPublisher.publish("scheduled-events", event);
        return ResponseEntity.ok("Scheduled event published");
    }
}
