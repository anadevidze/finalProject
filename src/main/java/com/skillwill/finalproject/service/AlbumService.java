package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.Album;
import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public List<Album> getAlbumsByArtist(Artist artist) {
        return albumRepository.findByArtist(artist);
    }

    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }

    public Album getAlbumById(Long albumId) {
        // Find the album by ID. If not found, throw an exception or handle it as needed.
        return albumRepository.findById(albumId).orElse(new Album());
    }
}
