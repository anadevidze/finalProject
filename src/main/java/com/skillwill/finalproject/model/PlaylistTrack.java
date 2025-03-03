package com.skillwill.finalproject.model;

import com.skillwill.finalproject.model.id.PlaylistTrackId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "playlist_tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PlaylistTrackId.class)
public class PlaylistTrack {
    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Id
    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;
}
