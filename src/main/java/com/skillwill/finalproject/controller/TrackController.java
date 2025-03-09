package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        Track createdTrack = trackService.createTrack(track);
        return ResponseEntity.ok(createdTrack);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<Optional<Track>> getTrack(@PathVariable Long trackId) {
        Optional<Track> track = trackService.getTrack(trackId);
        return ResponseEntity.ok(track);
    }
}