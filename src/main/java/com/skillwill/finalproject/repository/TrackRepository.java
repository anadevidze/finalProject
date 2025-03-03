package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.Album;
import com.skillwill.finalproject.model.Artist;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByAlbum(Album album);

    List<Track> findByArtist(Artist artist);

    List<Track> findByTitleContainingIgnoreCase(String title);
}