package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.ListeningHistory;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.User;
import com.skillwill.finalproject.repository.ListeningHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private ListeningHistoryRepository listeningHistoryRepository;

    // Tracks listening activity and saves data to ListeningHistory
    public void trackPlayCount(User user, Track track) {
        // Check if the listening history entry already exists for the user and track
        ListeningHistory existingHistory = listeningHistoryRepository.findByUserAndTrack(user, track)
                .orElse(new ListeningHistory(user, track, 0, track.getGenre()));

        // Increment the play count
        existingHistory.setPlayCount(existingHistory.getPlayCount() + 1);

        // Save or update the listening history entry
        listeningHistoryRepository.save(existingHistory);
    }
}
