package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Artist> getSimilarArtists(Long artistId) {
        return artistRepository.findSimilarArtistsByTracks(artistId);
    }

    public Artist getArtistById(Long artistId) {
        // Find the artist by ID. If not found, throw an exception or handle it as needed.
        return artistRepository.findById(artistId).orElse(new Artist());
    }
}

