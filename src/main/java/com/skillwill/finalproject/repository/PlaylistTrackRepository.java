package com.skillwill.finalproject.repository;

import com.skillwill.finalproject.model.Playlist;
import com.skillwill.finalproject.model.PlaylistTrack;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.id.PlaylistTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {
    List<PlaylistTrack> findByPlaylist(Playlist playlist);

    List<PlaylistTrack> findByTrack(Track track);

    void deleteByPlaylistAndTrack(Playlist playlist, Track track);
}

