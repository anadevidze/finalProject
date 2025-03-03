package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenre(String genre);

    @Query("SELECT DISTINCT a FROM Artist a JOIN Track t ON a.id = t.artist.id WHERE t.genre IN (SELECT t2.genre FROM Track t2 WHERE t2.artist.id = :artistId) AND a.id <> :artistId")
    List<Artist> findSimilarArtistsByTracks(@Param("artistId") Long artistId);
}
