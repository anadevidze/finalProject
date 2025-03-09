package com.skillwill.finalproject.service;

import com.skillwill.finalproject.enums.Status;
import com.skillwill.finalproject.model.User;
import com.skillwill.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedPassword");
    }

    @Test
    public void testRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals(Status.PENDING, savedUser.getStatus());
        assertNotNull(savedUser.getVerificationCode());
        verify(userRepository, times(1)).save(user);  // Verifies save was called once
    }

    @Test
    public void testVerifyUser_Success() {
        when(userRepository.findByVerificationCode(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean isVerified = userService.verifyUser("validVerificationCode");

        assertTrue(isVerified);
        assertEquals(Status.ACTIVE, user.getStatus());
        assertNull(user.getVerificationCode());
        verify(userRepository, times(1)).save(user);  // Verifies save was called once
    }

    @Test
    public void testVerifyUser_Failure() {
        when(userRepository.findByVerificationCode(anyString())).thenReturn(Optional.empty());

        boolean isVerified = userService.verifyUser("invalidVerificationCode");

        assertFalse(isVerified);
        verify(userRepository, times(0)).save(user);  // Verify that save was not called
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testGetUserById_Failure() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }
}