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

    public void trackPlayCount(User user, Track track) {
        ListeningHistory existingHistory = listeningHistoryRepository.findByUserAndTrack(user, track)
                .orElse(new ListeningHistory(user, track, 0, track.getGenre()));

        existingHistory.setPlayCount(existingHistory.getPlayCount() + 1);

        listeningHistoryRepository.save(existingHistory);
    }
}
