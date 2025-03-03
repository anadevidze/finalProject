package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Create a new artist
    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.createArtist(artist);
        return ResponseEntity.ok(createdArtist);
    }

    // Get artist details by ID
    @GetMapping("/{artistId}")
    public ResponseEntity<Artist> getArtist(@PathVariable Long artistId) {
        Artist artist = artistService.getArtistById(artistId);
        return ResponseEntity.ok(artist);
    }
}