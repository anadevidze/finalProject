package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVerificationCode(String verificationCode);
    Optional<User> findByEmail(String email);
}
