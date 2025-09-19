package org.zion.Zion.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaTopicManager {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicManager.class);

    // Topic names
    public static final String ACCOUNT_EVENTS_TOPIC = "account-events";
    public static final String SCHEDULED_EVENTS_TOPIC = "scheduled-events";

    // Create topics automatically at startup if they don't exist
    @Bean
    public NewTopic accountEventsTopic() {
        logger.info("🔄 Ensuring topic exists: {}", ACCOUNT_EVENTS_TOPIC);
        return TopicBuilder.name(ACCOUNT_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic scheduledEventsTopic() {
        logger.info("🔄 Ensuring topic exists: {}", SCHEDULED_EVENTS_TOPIC);
        return TopicBuilder.name(SCHEDULED_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    // ✅ Removed @PreDestroy cleanup to keep topics persistent
    // Topics will now survive app restarts and Kafka shutdowns
}
