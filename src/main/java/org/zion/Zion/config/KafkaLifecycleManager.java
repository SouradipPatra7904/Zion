package org.zion.Zion.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaLifecycleManager {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLifecycleManager.class);

    private static final String KAFKA_HOME = System.getProperty("user.home") + "/kafka_local";
    private static final String CONFIG_PATH = KAFKA_HOME + "/config/server.properties";

    private Process kafkaProcess;

    // Helper to run shell commands
    private Process runCommand(String... command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO(); // pipe logs to console
        return builder.start();
    }

    @PostConstruct
    public void startKafka() throws IOException {
        logger.info("Starting Kafka broker...");

        // Start Kafka broker without formatting / generating new cluster ID
        kafkaProcess = runCommand("/bin/bash", "-c",
                KAFKA_HOME + "/bin/kafka-server-start.sh " + CONFIG_PATH);

        logger.info("Kafka broker started. Existing data/logs will persist.");
    }

    @PreDestroy
    public void stopKafka() {
        logger.info("Stopping Kafka broker...");
        try {
            if (kafkaProcess != null && kafkaProcess.isAlive()) {
                kafkaProcess.destroy();
                kafkaProcess.waitFor();
            }
        } catch (Exception e) {
            logger.error("Error while stopping Kafka broker", e);
        }
    }
}
