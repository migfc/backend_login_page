package com.miguel.backend_login_page.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.miguel.backend_login_page.domain.user.User;

@DataJpaTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("hashedPassword");
    }

    @Test
    @DisplayName("Should save user successfully")
    void testSaveUser() {
        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        // Arrange
        userRepository.save(testUser);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("john@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("John Doe", foundUser.get().getName());
        assertEquals("john@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty Optional when email not found")
    void testFindByEmailNotFound() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("notexist@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Should find user by id")
    void testFindById() {
        // Arrange
        User savedUser = userRepository.save(testUser);

        // Act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("John Doe", foundUser.get().getName());
    }

    @Test
    @DisplayName("Should update user successfully")
    void testUpdateUser() {
        // Arrange
        User savedUser = userRepository.save(testUser);
        savedUser.setName("Jane Doe");

        // Act
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("john@example.com", updatedUser.getEmail());
    }

    @Test
    @DisplayName("Should delete user successfully")
    void testDeleteUser() {
        // Arrange
        User savedUser = userRepository.save(testUser);
        String userId = savedUser.getId();

        // Act
        userRepository.deleteById(userId);
        Optional<User> deletedUser = userRepository.findById(userId);

        // Assert
        assertFalse(deletedUser.isPresent());
    }

    @Test
    @DisplayName("Should handle multiple users with different emails")
    void testFindByEmailWithMultipleUsers() {
        // Arrange
        User user1 = new User();
        user1.setName("User One");
        user1.setEmail("user1@example.com");
        user1.setPassword("pass1");

        User user2 = new User();
        user2.setName("User Two");
        user2.setEmail("user2@example.com");
        user2.setPassword("pass2");

        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        Optional<User> foundUser1 = userRepository.findByEmail("user1@example.com");
        Optional<User> foundUser2 = userRepository.findByEmail("user2@example.com");

        // Assert
        assertTrue(foundUser1.isPresent());
        assertTrue(foundUser2.isPresent());
        assertEquals("User One", foundUser1.get().getName());
        assertEquals("User Two", foundUser2.get().getName());
    }
}
