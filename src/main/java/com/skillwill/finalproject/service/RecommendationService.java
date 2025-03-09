package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.*;
import com.skillwill.finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListeningHistoryRepository listeningHistoryRepository;

    public List<Playlist> recommendPlaylists(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<ListeningHistory> listeningHistoryList = listeningHistoryRepository.findByUser(user);

        Map<String, Long> genreCountMap = listeningHistoryList.stream()
                .collect(Collectors.groupingBy(listeningHistory -> listeningHistory.getTrack().getGenre(), Collectors.counting()));

        List<String> topGenres = genreCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Playlist> allPlaylists = playlistRepository.findByUser(user);

        return allPlaylists.stream()
                .filter(playlist -> playlist.getTracks().stream()
                        .anyMatch(track -> topGenres.contains(track.getGenre())))
                .collect(Collectors.toList());
    }

    public List<Artist> recommendArtists(User user) {
        List<ListeningHistory> listeningHistoryList = listeningHistoryRepository.findByUser(Optional.ofNullable(user));

        Map<Artist, Long> artistCountMap = listeningHistoryList.stream()
                .collect(Collectors.groupingBy(listeningHistory -> listeningHistory.getTrack().getArtist(), Collectors.counting()));

        return artistCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}