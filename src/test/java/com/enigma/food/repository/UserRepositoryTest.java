package com.enigma.food.repository;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Create_ReturnCreatedUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setBalance(1000);
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);

        User userCustomer = userRepository.findById(user.getId()).orElse(null);

        assertThat(userCustomer).isNotNull();
    }

//    @Mock
//    private UserRepository userRepository;
//
//    private User user;
//
//    @BeforeEach
//    public void setUp() {
//        user = new User();
//        user.setUsername("testuser");
//        user.setPassword("testpassword");
//        user.setBalance(1000);
//        user.setRole(Role.ROLE_USER);
//    }
//
//    @Test
//    public void whenFindByUsername_thenReturnUser() {
//        // Arrange
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//        // Act
//        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
//
//        // Assert
//        assertTrue(foundUser.isPresent());
//        assertEquals(user.getUsername(), foundUser.get().getUsername());
//        verify(userRepository).findByUsername(user.getUsername());
//    }
//
//    @Test
//    public void whenFindByUsername_thenReturnEmpty() {
//        // Arrange
//        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
//
//        // Act
//        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");
//
//        // Assert
//        assertFalse(foundUser.isPresent());
//        verify(userRepository).findByUsername("nonexistentuser");
//    }


}