package com.skillwill.finalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "listening_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListeningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    private int playCount = 0;

    private String genre;

    public ListeningHistory(User user, Track track, int i, String genre) {
        this.user = user;
        this.track = track;
        this.playCount = i;
        this.genre = genre;
    }

    public ListeningHistory(User user, Track track, int i) {
        this.user = user;
        this.track = track;
        this.playCount = i;
    }
}