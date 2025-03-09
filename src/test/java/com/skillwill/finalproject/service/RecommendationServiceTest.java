package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.*;
import com.skillwill.finalproject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ListeningHistoryRepository listeningHistoryRepository;

    private User user;
    private Track track1, track2, track3;
    private Playlist playlist1, playlist2;
    private ListeningHistory history1, history2, history3;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        track1 = new Track(1L, new Album(), "Rock Song", "Rock", new Artist(), 10);
        track2 = new Track(2L, new Album(), "Pop Song", "Pop", new Artist(), 20);
        track3 = new Track(3L, new Album(), "Jazz Song", "Jazz", new Artist(), 15);

        history1 = new ListeningHistory(user, track1, 5, "Rock");
        history2 = new ListeningHistory(user, track2, 3, "Pop");
        history3 = new ListeningHistory(user, track3, 7, "Jazz");

        playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setUser(user);
        playlist1.setName("Rock Playlist");

        playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setUser(user);
        playlist2.setName("Pop Playlist");
    }

    @Test
    void recommendPlaylists_ShouldReturnPlaylistsMatchingTopGenres() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(listeningHistoryRepository.findByUser(Optional.of(user))).thenReturn(Arrays.asList(history1, history2, history3));
        when(playlistRepository.findByUser(Optional.of(user))).thenReturn(Arrays.asList(playlist1, playlist2));

        List<Playlist> recommendedPlaylists = recommendationService.recommendPlaylists(1L);

        assertNotNull(recommendedPlaylists);
        assertFalse(recommendedPlaylists.isEmpty());
    }

    @Test
    void recommendPlaylists_ShouldReturnEmptyListWhenNoListeningHistory() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(listeningHistoryRepository.findByUser(Optional.of(user))).thenReturn(Collections.emptyList());
        when(playlistRepository.findByUser(Optional.of(user))).thenReturn(Arrays.asList(playlist1, playlist2));

        List<Playlist> recommendedPlaylists = recommendationService.recommendPlaylists(1L);

        assertNotNull(recommendedPlaylists);
        assertTrue(recommendedPlaylists.isEmpty());
    }

    @Test
    void recommendArtists_ShouldReturnTopArtists() {
        Artist artist1 = new Artist();
        Artist artist2 = new Artist();
        Artist artist3 = new Artist();
        track1.setArtist(artist1);
        track2.setArtist(artist2);
        track3.setArtist(artist3);

        when(listeningHistoryRepository.findByUser(Optional.of(user)))
                .thenReturn(Arrays.asList(history1, history2, history3));

        List<Artist> recommendedArtists = recommendationService.recommendArtists(user);

        assertNotNull(recommendedArtists);
        assertFalse(recommendedArtists.isEmpty());
        assertEquals(3, recommendedArtists.size());
    }
}