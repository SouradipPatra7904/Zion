package org.zion.Zion.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PreDestroy;

@Configuration
public class DatabaseConfig {

    private static final String DB_NAME = "zion_db";
    private static final String ADMIN_URL = "jdbc:postgresql://localhost:5432/pre_connect_db";
    private static final String USER = "diamondback";
    private static final String PASSWORD = "diamondback";

    @Bean
    public DataSource dataSource() throws Exception {
        // Step 1: Create DB if not exists
        try (Connection conn = DriverManager.getConnection(ADMIN_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + DB_NAME);
            stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
            System.out.println("Database ensured: " + DB_NAME);
        }

        // Step 2: Point Hibernate to the new DB
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/" + DB_NAME);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        return ds;
    }

    @PreDestroy
    public void cleanup() throws Exception {
        try (Connection conn = DriverManager.getConnection(ADMIN_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + DB_NAME);
            System.out.println("Dropped database: " + DB_NAME);
        }
    }
}
