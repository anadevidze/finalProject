package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.*;
import com.skillwill.finalproject.repository.ListeningHistoryRepository;
import com.skillwill.finalproject.repository.TrackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private ListeningHistoryRepository listeningHistoryRepository;

    @InjectMocks
    private TrackService trackService;

    private Track track;
    private Album album;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        album = new Album();
        album.setId(1L);
        album.setTitle("Test Album");

        track = new Track();
        track.setId(1L);
        track.setTitle("Test Track");
        track.setGenre("Rock");
        track.setAlbum(album);
        track.setPlayCount(5);
    }

    @Test
    void testCreateTrack() {
        when(trackRepository.save(any(Track.class))).thenReturn(track);

        Track createdTrack = trackService.createTrack(track);

        assertNotNull(createdTrack);
        assertEquals("Test Track", createdTrack.getTitle());
        verify(trackRepository, times(1)).save(track);
    }

    @Test
    void testGetTracksByAlbum() {
        when(trackRepository.findByAlbum(any(Album.class))).thenReturn(Arrays.asList(track));

        List<Track> tracks = trackService.getTracksByAlbum(album);

        assertNotNull(tracks);
        assertEquals(1, tracks.size());
        assertEquals("Test Track", tracks.get(0).getTitle());
        verify(trackRepository, times(1)).findByAlbum(album);
    }

    @Test
    void testSearchTracksByTitle() {
        when(trackRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(track));

        List<Track> tracks = trackService.searchTracksByTitle("test");

        assertNotNull(tracks);
        assertEquals(1, tracks.size());
        assertEquals("Test Track", tracks.get(0).getTitle());
        verify(trackRepository, times(1)).findByTitleContainingIgnoreCase("test");
    }

    @Test
    void testGetTrack_Success() {
        when(trackRepository.findById(1L)).thenReturn(Optional.of(track));

        Optional<Track> foundTrack = trackService.getTrack(1L);

        assertTrue(foundTrack.isPresent());
        assertEquals("Test Track", foundTrack.get().getTitle());
        verify(trackRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTrack_NotFound() {
        when(trackRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Track> foundTrack = trackService.getTrack(1L);

        assertFalse(foundTrack.isPresent());
        verify(trackRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTrack() {
        Track updatedTrack = new Track();
        updatedTrack.setTitle("Updated Track");
        updatedTrack.setGenre("Pop");
        updatedTrack.setAlbum(album);
        updatedTrack.setPlayCount(10);

        when(trackRepository.findById(1L)).thenReturn(Optional.of(track));
        when(trackRepository.save(any(Track.class))).thenReturn(updatedTrack);

        Track result = trackService.updateTrack(1L, updatedTrack);

        assertNotNull(result);
        assertEquals("Updated Track", result.getTitle());
        assertEquals("Pop", result.getGenre());
        assertEquals(10, result.getPlayCount());
        verify(trackRepository, times(1)).save(track);
    }

    @Test
    void testDeleteTrack() {
        doNothing().when(trackRepository).deleteById(1L);

        trackService.deleteTrack(1L);

        verify(trackRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIncrementPlayCount() {
        when(trackRepository.findById(1L)).thenReturn(Optional.of(track));
        when(trackRepository.save(any(Track.class))).thenReturn(track);

        trackService.incrementPlayCount(1L);

        assertEquals(6, track.getPlayCount());
        verify(trackRepository, times(1)).save(track);
    }

    @Test
    void testLogTrackPlay_NewEntry() {
        when(listeningHistoryRepository.findByUserAndTrack(user, track)).thenReturn(Optional.empty());
        when(listeningHistoryRepository.save(any(ListeningHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        trackService.logTrackPlay(user, track);

        verify(listeningHistoryRepository, times(1)).save(any(ListeningHistory.class));
    }

    @Test
    void testLogTrackPlay_ExistingEntry() {
        ListeningHistory history = new ListeningHistory(user, track, 3, track.getGenre());
        when(listeningHistoryRepository.findByUserAndTrack(user, track)).thenReturn(Optional.of(history));
        when(listeningHistoryRepository.save(any(ListeningHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        trackService.logTrackPlay(user, track);

        assertEquals(4, history.getPlayCount());
        verify(listeningHistoryRepository, times(1)).save(history);
    }
}