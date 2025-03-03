package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUser(Optional<User> user);
}