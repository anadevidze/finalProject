package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.Album;
import com.skillwill.finalproject.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist(Artist artist);
}