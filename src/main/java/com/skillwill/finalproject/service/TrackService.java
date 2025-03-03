package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.*;
import com.skillwill.finalproject.repository.TrackRepository;
import com.skillwill.finalproject.repository.ListeningHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ListeningHistoryRepository listeningHistoryRepository;

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getTracksByAlbum(Album album) {
        return trackRepository.findByAlbum(album);
    }

    public List<Track> getTracksByArtist(Artist artist) {
        return trackRepository.findByArtist(artist);
    }

    public List<Track> searchTracksByTitle(String title) {
        return trackRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Track> getTrack(Long trackId) {
        return trackRepository.findById(trackId);
    }

    public Track updateTrack(Long trackId, Track updatedTrack) {
        Track existingTrack = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found"));

        existingTrack.setTitle(updatedTrack.getTitle());
        existingTrack.setGenre(updatedTrack.getGenre());
        existingTrack.setAlbum(updatedTrack.getAlbum());
        existingTrack.setArtist(updatedTrack.getArtist());
        existingTrack.setPlayCount(updatedTrack.getPlayCount());

        return trackRepository.save(existingTrack);
    }

    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }

    public void incrementPlayCount(Long trackId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found"));
        track.setPlayCount(track.getPlayCount() + 1);
        trackRepository.save(track);
    }

    public void logTrackPlay(User user, Track track) {
        ListeningHistory listeningHistory = listeningHistoryRepository
                .findByUserAndTrack(user, track)
                .orElse(new ListeningHistory(user, track, 0, track.getGenre()));

        listeningHistory.setPlayCount(listeningHistory.getPlayCount() + 1);
        listeningHistoryRepository.save(listeningHistory);
    }
}