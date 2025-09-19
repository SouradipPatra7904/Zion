package org.zion.Zion.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.zion.Zion.query.AccountProjectionService;

@Service
public class EventConsumer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AccountProjectionService projectionService;

    public EventConsumer(AccountProjectionService projectionService) {
        this.projectionService = projectionService;
    }

    @KafkaListener(topics = "account-events", groupId = "zion-read", containerFactory = "kafkaListenerContainerFactory")
    public void handleAccountEvent(String json) throws Exception {
        JsonNode node = mapper.readTree(json);

        // If producer used POJOs, payload will be JSON representation of DepositMadeEvent
        // Can be adapted according to the event shape. Here we assume fields: accountId, amount, timestamp
        
        if (node.has("accountId") && node.has("amount")) {
            String accountId = node.get("accountId").asText();
            double amount = node.get("amount").asDouble();
            projectionService.applyDeposit(accountId, amount);
            System.out.printf("Applied deposit: account=%s amount=%s%n", accountId, amount);
        }
        else {
            System.out.println("Unknown event shape: " + json);
        }
    }
}
