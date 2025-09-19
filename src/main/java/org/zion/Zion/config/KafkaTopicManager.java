package org.zion.Zion.config;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class KafkaTopicManager {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicManager.class);

    // ✅ Define topic names
    public static final String ACCOUNT_EVENTS_TOPIC = "account-events";
    public static final String SCHEDULED_EVENTS_TOPIC = "scheduled-events";

    private final String bootstrapServers = "localhost:9092";

    // ✅ Create topics automatically at startup
    @Bean
    public NewTopic accountEventsTopic() {
        logger.info("🔄 Creating topic: {}", ACCOUNT_EVENTS_TOPIC);
        return TopicBuilder.name(ACCOUNT_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1) // since local Kafka
                .build();
    }

    @Bean
    public NewTopic scheduledEventsTopic() {
        logger.info("🔄 Creating topic: {}", SCHEDULED_EVENTS_TOPIC);
        return TopicBuilder.name(SCHEDULED_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    // ✅ Delete topics on shutdown
    @PreDestroy
    public void cleanupTopics() {
        logger.info("🧹 Deleting topics: {}, {}", ACCOUNT_EVENTS_TOPIC, SCHEDULED_EVENTS_TOPIC);

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient admin = AdminClient.create(props)) {
            List<String> topics = Arrays.asList(ACCOUNT_EVENTS_TOPIC, SCHEDULED_EVENTS_TOPIC);
            DeleteTopicsResult result = admin.deleteTopics(topics);
            result.all().get();
            logger.info("✅ Topics deleted successfully");
        } catch (Exception e) {
            logger.error("⚠️ Failed to delete topics", e);
        }
    }
}
