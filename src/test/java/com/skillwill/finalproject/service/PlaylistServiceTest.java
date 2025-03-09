package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.User;
import com.skillwill.finalproject.repository.PlaylistRepository;
import com.skillwill.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlaylistService playlistService;

    private Playlist playlist;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setUser(user);
        playlist.setName("Chill Vibes");
        playlist.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testCreatePlaylist() {
        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlist);

        Playlist createdPlaylist = playlistService.createPlaylist(playlist);

        assertNotNull(createdPlaylist);
        assertEquals("Chill Vibes", createdPlaylist.getName());
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    void testGetPlaylistsByUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(playlistRepository.findByUser(any(Optional.class))).thenReturn(Arrays.asList(playlist));

        List<Playlist> playlists = playlistService.getPlaylistsByUser(1L);

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        verify(playlistRepository, times(1)).findByUser(Optional.of(user));
    }

    @Test
    void testGetPlaylistById_Success() {
        when(playlistRepository.findById(anyLong())).thenReturn(Optional.of(playlist));

        Optional<Playlist> foundPlaylist = playlistService.getPlaylist(1L);

        assertTrue(foundPlaylist.isPresent());
        assertEquals("Chill Vibes", foundPlaylist.get().getName());
        verify(playlistRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPlaylistById_NotFound() {
        when(playlistRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Playlist> foundPlaylist = playlistService.getPlaylist(1L);

        assertFalse(foundPlaylist.isPresent());
    }

    @Test
    void testUpdatePlaylist_Success() {
        Playlist updatedPlaylist = new Playlist(1L, user, "Rock Classics", LocalDateTime.now(), null);

        when(playlistRepository.findById(anyLong())).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(any(Playlist.class))).thenReturn(updatedPlaylist);

        Playlist result = playlistService.updatePlaylist(1L, updatedPlaylist);

        assertNotNull(result);
        assertEquals("Rock Classics", result.getName());
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    void testUpdatePlaylist_NotFound() {
        when(playlistRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            playlistService.updatePlaylist(1L, playlist);
        });

        assertEquals("Playlist not found", exception.getMessage());
    }

    @Test
    void testDeletePlaylist() {
        doNothing().when(playlistRepository).deleteById(anyLong());

        playlistService.deletePlaylist(1L);

        verify(playlistRepository, times(1)).deleteById(1L);
    }
}

