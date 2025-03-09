package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.PlaylistTrack;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.id.PlaylistTrackId;
import com.skillwill.finalproject.repository.PlaylistRepository;
import com.skillwill.finalproject.repository.PlaylistTrackRepository;
import com.skillwill.finalproject.repository.TrackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistTrackServiceTest {

    @Mock
    private PlaylistTrackRepository playlistTrackRepository;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private PlaylistTrackService playlistTrackService;

    private Playlist playlist;
    private Track track;
    private PlaylistTrack playlistTrack;
    private PlaylistTrackId playlistTrackId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        playlist = new Playlist();
        playlist.setId(1L);

        track = new Track();
        track.setId(1L);

        playlistTrack = new PlaylistTrack();
        playlistTrack.setPlaylist(playlist);
        playlistTrack.setTrack(track);

        playlistTrackId = new PlaylistTrackId(playlist.getId(), track.getId());
    }

    @Test
    void testAddTrackToPlaylist() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackRepository.findById(1L)).thenReturn(Optional.of(track));
        when(playlistTrackRepository.save(any(PlaylistTrack.class))).thenReturn(playlistTrack);

        PlaylistTrack addedTrack = playlistTrackService.addTrackToPlaylist(1L, 1L);

        assertNotNull(addedTrack);
        assertEquals(1L, addedTrack.getPlaylist().getId());
        assertEquals(1L, addedTrack.getTrack().getId());
        verify(playlistTrackRepository, times(1)).save(any(PlaylistTrack.class));
    }

    @Test
    void testGetTracksByPlaylist() {
        when(playlistTrackRepository.findByPlaylist(any(Playlist.class))).thenReturn(Arrays.asList(playlistTrack));

        List<PlaylistTrack> tracks = playlistTrackService.getTracksByPlaylist(playlist);

        assertNotNull(tracks);
        assertEquals(1, tracks.size());
        verify(playlistTrackRepository, times(1)).findByPlaylist(playlist);
    }

    @Test
    void testGetTracksByTrack() {
        when(playlistTrackRepository.findByTrack(any(Track.class))).thenReturn(Arrays.asList(playlistTrack));

        List<PlaylistTrack> tracks = playlistTrackService.getTracksByTrack(track);

        assertNotNull(tracks);
        assertEquals(1, tracks.size());
        verify(playlistTrackRepository, times(1)).findByTrack(track);
    }

    @Test
    void testRemoveTrackFromPlaylist() {
        doNothing().when(playlistTrackRepository).deleteByPlaylistAndTrack(any(Playlist.class), any(Track.class));

        playlistTrackService.removeTrackFromPlaylist(playlist, track);

        verify(playlistTrackRepository, times(1)).deleteByPlaylistAndTrack(playlist, track);
    }

    @Test
    void testGetPlaylistTrackById_Success() {
        when(playlistTrackRepository.findById(any(PlaylistTrackId.class))).thenReturn(Optional.of(playlistTrack));

        Optional<PlaylistTrack> foundTrack = playlistTrackService.getPlaylistTrack(playlistTrackId);

        assertTrue(foundTrack.isPresent());
        assertEquals(1L, foundTrack.get().getPlaylist().getId());
        assertEquals(1L, foundTrack.get().getTrack().getId());
        verify(playlistTrackRepository, times(1)).findById(playlistTrackId);
    }

    @Test
    void testGetPlaylistTrackById_NotFound() {
        when(playlistTrackRepository.findById(any(PlaylistTrackId.class))).thenReturn(Optional.empty());

        Optional<PlaylistTrack> foundTrack = playlistTrackService.getPlaylistTrack(playlistTrackId);

        assertFalse(foundTrack.isPresent());
        verify(playlistTrackRepository, times(1)).findById(playlistTrackId);
    }
}
