package com.skillwill.finalproject.model.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackId implements java.io.Serializable {
    private Long playlist;
    private Long track;
}
