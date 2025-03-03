package com.skillwill.finalproject.service;
import com.skillwill.finalproject.enums.Status;
import com.skillwill.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.skillwill.finalproject.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setStatus(Status.PENDING);
        user.setVerificationCode(generateVerificationCode());
        return userRepository.save(user);
    }

    public boolean verifyUser(String code) {
        Optional<User> userOptional = userRepository.findByVerificationCode(code);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(Status.ACTIVE);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private String generateVerificationCode() {
        return java.util.UUID.randomUUID().toString();
    }

    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }
}

