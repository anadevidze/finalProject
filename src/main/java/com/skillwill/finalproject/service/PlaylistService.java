package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.User;
import com.skillwill.finalproject.repository.PlaylistRepository;
import com.skillwill.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return playlistRepository.findByUser(user);
    }

    public Optional<Playlist> getPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    public Playlist updatePlaylist(Long playlistId, Playlist updatedPlaylist) {
        Playlist existingPlaylist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        existingPlaylist.setName(updatedPlaylist.getName());
        existingPlaylist.setCreatedAt(updatedPlaylist.getCreatedAt());
        existingPlaylist.setUser(updatedPlaylist.getUser());

        return playlistRepository.save(existingPlaylist);
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }
}