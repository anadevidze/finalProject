package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.PlaylistTrack;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.id.PlaylistTrackId;
import com.skillwill.finalproject.repository.PlaylistRepository;
import com.skillwill.finalproject.repository.PlaylistTrackRepository;
import com.skillwill.finalproject.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistTrackService {

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackRepository trackRepository;

    public PlaylistTrack addTrackToPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(new Playlist());
        Track track = trackRepository.findById(trackId).orElse(new Track());

        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setPlaylist(playlist);
        playlistTrack.setTrack(track);

        return playlistTrackRepository.save(playlistTrack);
    }

    public List<PlaylistTrack> getTracksByPlaylist(Playlist playlist) {
        return playlistTrackRepository.findByPlaylist(playlist);
    }

    public List<PlaylistTrack> getTracksByTrack(Track track) {
        return playlistTrackRepository.findByTrack(track);
    }

    public void removeTrackFromPlaylist(Playlist playlist, Track track) {
        playlistTrackRepository.deleteByPlaylistAndTrack(playlist, track);
    }

    public Optional<PlaylistTrack> getPlaylistTrack(PlaylistTrackId playlistTrackId) {
        return playlistTrackRepository.findById(playlistTrackId);
    }
}
