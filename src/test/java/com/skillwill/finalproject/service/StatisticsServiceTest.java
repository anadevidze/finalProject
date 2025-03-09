package com.skillwill.finalproject.service;

import com.skillwill.finalproject.model.ListeningHistory;
import com.skillwill.finalproject.model.Track;
import com.skillwill.finalproject.model.User;
import com.skillwill.finalproject.repository.ListeningHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {

    @Mock
    private ListeningHistoryRepository listeningHistoryRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    private User user;
    private Track track;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        track = new Track();
        track.setId(1L);
        track.setTitle("Test Track");
        track.setGenre("Rock");
    }

    @Test
    void testTrackPlayCount_NewEntry() {
        when(listeningHistoryRepository.findByUserAndTrack(user, track)).thenReturn(Optional.empty());
        when(listeningHistoryRepository.save(any(ListeningHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        statisticsService.trackPlayCount(user, track);

        verify(listeningHistoryRepository, times(1)).save(any(ListeningHistory.class));
    }

    @Test
    void testTrackPlayCount_ExistingEntry() {
        ListeningHistory existingHistory = new ListeningHistory(user, track, 3, track.getGenre());
        when(listeningHistoryRepository.findByUserAndTrack(user, track)).thenReturn(Optional.of(existingHistory));
        when(listeningHistoryRepository.save(any(ListeningHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        statisticsService.trackPlayCount(user, track);

        assertEquals(4, existingHistory.getPlayCount());
        verify(listeningHistoryRepository, times(1)).save(existingHistory);
    }
}