package com.skillwill.finalproject.controller;

import com.skillwill.finalproject.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // Get recommendations for the user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRecommendations(@PathVariable Long userId) {
        var recommendations = recommendationService.recommendPlaylists(userId);
        return ResponseEntity.ok(recommendations);
    }
}