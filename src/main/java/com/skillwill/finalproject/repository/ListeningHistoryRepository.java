package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.ListeningHistory;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {
    Optional<ListeningHistory> findByUserAndTrack(User user, Track track);

    List<ListeningHistory> findByUser(Optional<User> user);
}
