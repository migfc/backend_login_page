package com.miguel.backend_login_page.infra.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.miguel.backend_login_page.domain.user.User;

@SpringBootTest
@TestPropertySource(properties = "api.security.token.secret=test-secret-key-for-testing-purposes")
@DisplayName("TokenService Tests")
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("1");
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("hashedPassword");
    }

    @Test
    @DisplayName("Should generate valid token for user")
    void testGenerateTokenSuccess() {
        // Act
        String token = tokenService.generateToken(testUser);

        // Assert
        assertNotNull(token);
        assertTrue(token.contains("."));
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    @DisplayName("Should validate token and return subject")
    void testValidateTokenSuccess() {
        // Arrange
        String token = tokenService.generateToken(testUser);

        // Act
        String subject = tokenService.valideteToken(token);

        // Assert
        assertNotNull(subject);
        assertTrue(subject.equals("john@example.com"));
    }

    @Test
    @DisplayName("Should return null for invalid token")
    void testValidateInvalidToken() {
        // Act
        String result = tokenService.valideteToken("invalid.token.here");

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null for empty token")
    void testValidateEmptyToken() {
        // Act
        String result = tokenService.valideteToken("");

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should generate valid token with correct format")
    void testGenerateTokenFormat() {
        // Act
        String token = tokenService.generateToken(testUser);

        // Assert
        assertNotNull(token);
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);
        for (String part : parts) {
            assertNotNull(part);
            assertTrue(part.length() > 0);
        }
    }
}
