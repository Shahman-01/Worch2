package com.worch.tests.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(OrderAnnotation.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    static void waitForKeycloak() {
        System.out.println("Keycloak URL: " + keycloak.getAuthServerUrl());
        System.out.println("Keycloak Admin: " + keycloak.getAdminUsername());

        try {
            String healthUrl = keycloak.getAuthServerUrl() + "/health/ready";
            System.out.println("Health check URL: " + healthUrl);
        } catch (Exception e) {
            System.err.println("Keycloak health check failed: " + e.getMessage());
        }
    }

    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @Container
    protected static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:22.0")
            .withRealmImportFile("test-realm.json")
            .withAdminUsername("admin")
            .withAdminPassword("admin")
            .withEnv("KC_HEALTH_ENABLED", "true")
            .withEnv("KC_DB", "dev-mem")
            .withEnv("KC_LOG_LEVEL", "INFO")
            .withReuse(true)
            .waitingFor(Wait.forHttp("/health/ready")
                    .forPort(8080)
                    .withStartupTimeout(Duration.ofMinutes(3)))
            .withStartupTimeout(Duration.ofMinutes(3));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.flyway.enabled", () -> "false");

        registry.add("telegram.bot.username", () -> "test_bot");
        registry.add("telegram.bot.token", () -> "test_token");

        String keycloakUrl = keycloak.getAuthServerUrl();
        registry.add("keycloak.auth-server-url", () -> keycloakUrl);
        registry.add("keycloak.realm", () -> "test");
        registry.add("keycloak.client-id", () -> "test-client");
        registry.add("keycloak.client-secret", () -> "test-secret");

        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloakUrl + "/realms/test");
        registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri",
                () -> keycloakUrl + "/realms/test");
        registry.add("spring.security.oauth2.client.registration.keycloak.client-id",
                () -> "test-client");
        registry.add("spring.security.oauth2.client.registration.keycloak.client-secret",
                () -> "test-secret");
    }
}
