package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.model.Album;
import com.skillwill.finalproject.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Create a new album
    @PostMapping("/create-album")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        Album createdAlbum = albumService.createAlbum(album);
        return ResponseEntity.ok(createdAlbum);
    }

    // Get album details by ID
    @GetMapping("/{albumId}")
    public ResponseEntity<Album> getAlbum(@PathVariable Long albumId) {
        Album album = albumService.getAlbumById(albumId);
        return ResponseEntity.ok(album);
    }
}