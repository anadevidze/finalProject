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

    // This method generates playlist recommendations based on top 3 genres of the user
    public List<Playlist> recommendPlaylists(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        // Get all listening statistics for the user
        List<ListeningHistory> listeningHistoryList = listeningHistoryRepository.findByUser(user);

        // Count the occurrence of each track's genre in the listening history
        Map<String, Long> genreCountMap = listeningHistoryList.stream()
                .collect(Collectors.groupingBy(listeningHistory -> listeningHistory.getTrack().getGenre(), Collectors.counting()));

        // Get top 3 most listened genres
        List<String> topGenres = genreCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Generate recommended playlists based on top genres
        List<Playlist> allPlaylists = playlistRepository.findByUser(user);

        // Filter playlists that match top genres based on track genres
        return allPlaylists.stream()
                .filter(playlist -> playlist.getTracks().stream()
                        .anyMatch(track -> topGenres.contains(track.getGenre())))
                .collect(Collectors.toList());
    }

    // This method generates artist recommendations based on user's most listened artists
    public List<Artist> recommendArtists(User user) {
        // Get all listening statistics for the user
        List<ListeningHistory> listeningHistoryList = listeningHistoryRepository.findByUser(Optional.ofNullable(user));

        // Get the most listened artists
        Map<Artist, Long> artistCountMap = listeningHistoryList.stream()
                .collect(Collectors.groupingBy(listeningHistory -> listeningHistory.getTrack().getArtist(), Collectors.counting()));

        return artistCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}