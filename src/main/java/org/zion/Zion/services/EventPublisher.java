package org.zion.Zion.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventPublisher(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, Object event){
        kafkaTemplate.send(topic, event);
        kafkaTemplate.flush();
    }

    //Update & Read model

    //helper to block during testing (optional)
    public void publishAndWait(String topic, Object event) throws Exception{
        kafkaTemplate.send(topic, event).get();
    }
}
