package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.service.PlaylistTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist-tracks")
public class PlaylistTrackController {

    @Autowired
    private PlaylistTrackService playlistTrackService;

    @PostMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<String> addTrackToPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        playlistTrackService.addTrackToPlaylist(playlistId, trackId);
        return ResponseEntity.ok("Track added to playlist");
    }
}