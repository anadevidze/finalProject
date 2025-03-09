package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Album;
import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private Artist artist;
    private Album album;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        artist = new Artist();
        artist.setId(1L);
        artist.setStageName("Test Artist");
        artist.setGenre("Rock");

        album = new Album();
        album.setId(1L);
        album.setArtist(artist);
        album.setTitle("Test Album");
        album.setReleaseDate(LocalDateTime.now());
    }

    @Test
    void testCreateAlbum() {
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        Album createdAlbum = albumService.createAlbum(album);

        assertNotNull(createdAlbum);
        assertEquals("Test Album", createdAlbum.getTitle());
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void testGetAlbumsByArtist() {
        when(albumRepository.findByArtist(any(Artist.class))).thenReturn(Arrays.asList(album));

        List<Album> albums = albumService.getAlbumsByArtist(artist);

        assertNotNull(albums);
        assertEquals(1, albums.size());
        assertEquals("Test Album", albums.get(0).getTitle());
        verify(albumRepository, times(1)).findByArtist(artist);
    }

    @Test
    void testDeleteAlbum() {
        doNothing().when(albumRepository).deleteById(1L);

        albumService.deleteAlbum(1L);

        verify(albumRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAlbumById_Success() {
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        Album foundAlbum = albumService.getAlbumById(1L);

        assertNotNull(foundAlbum);
        assertEquals("Test Album", foundAlbum.getTitle());
        verify(albumRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAlbumById_NotFound() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        Album foundAlbum = albumService.getAlbumById(1L);

        assertNotNull(foundAlbum);
        assertNull(foundAlbum.getTitle()); // Since a new empty Album is returned
        verify(albumRepository, times(1)).findById(1L);
    }
}