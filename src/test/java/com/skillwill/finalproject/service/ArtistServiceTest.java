package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    private Artist artist;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock artist
        artist = new Artist();
        artist.setId(1L);
        artist.setStageName("The Rocker");
        artist.setGenre("Rock");
    }

    @Test
    public void testCreateArtist() {
        // Mock behavior for artistRepository.save()
        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Artist createdArtist = artistService.createArtist(artist);

        assertNotNull(createdArtist);
        assertEquals("The Rocker", createdArtist.getStageName());
        verify(artistRepository, times(1)).save(artist);  // Verifies save was called once
    }

    @Test
    public void testGetSimilarArtists() {
        // Mock behavior for findSimilarArtistsByTracks()
        Artist similarArtist = new Artist(2L, artist.getUser(), "The Drummer", "Rock");
        List<Artist> similarArtists = Arrays.asList(similarArtist);
        when(artistRepository.findSimilarArtistsByTracks(anyLong())).thenReturn(similarArtists);

        List<Artist> result = artistService.getSimilarArtists(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Drummer", result.get(0).getStageName());
        verify(artistRepository, times(1)).findSimilarArtistsByTracks(1L);  // Verifies that the repository method was called once
    }

    @Test
    public void testGetArtistById_Success() {
        // Mock behavior for findById()
        when(artistRepository.findById(anyLong())).thenReturn(Optional.of(artist));

        Artist foundArtist = artistService.getArtistById(1L);

        assertNotNull(foundArtist);
        assertEquals(artist.getStageName(), foundArtist.getStageName());
    }

    @Test
    public void testGetArtistById_Failure() {
        // Mock behavior for findById()
        when(artistRepository.findById(anyLong())).thenReturn(Optional.empty());

        Artist foundArtist = artistService.getArtistById(1L);

        assertNotNull(foundArtist);
        assertEquals(0L, foundArtist.getId());  // Default artist with empty ID
    }
}