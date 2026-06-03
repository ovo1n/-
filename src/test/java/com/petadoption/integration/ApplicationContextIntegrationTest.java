package com.petadoption.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ApplicationContextIntegrationTest {

    @Test
    public void contextLoads() {
        // If the Spring context fails to start, this test will fail.
    }
}
