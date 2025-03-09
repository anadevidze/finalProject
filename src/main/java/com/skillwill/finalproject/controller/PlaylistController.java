package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/create-playlist")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPlaylists(@PathVariable Long userId) {
        var playlists = playlistService.getPlaylistsByUser(userId);
        return ResponseEntity.ok(playlists);
    }
}