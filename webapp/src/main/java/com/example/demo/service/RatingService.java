package com.example.demo.service;

import com.example.demo.model.Rating;
import com.example.demo.model.annonceDetails;
import com.example.demo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private DetailsService detailsService;

    public List<Rating> getRatingsForAnnonce(int annonceId) {
        return ratingRepository.findByAnnonceDetailsId(annonceId);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public void submitRating(int annonceId, int ratingValue) {

        annonceDetails annonceDetails = detailsService.findById(annonceId); // Fetch from DB

        if (annonceDetails == null) {
            throw new IllegalArgumentException("AnnonceDetails not found for ID: " + annonceId);
        }

        Rating rating = new Rating();
        rating.setRatingValue(ratingValue);
        rating.setAnnonceDetails(annonceDetails);

        // Save the Rating object to the database
        ratingRepository.save(rating);
    }


    public double getAverageRating(int annonceId) {
        List<Rating> ratings = ratingRepository.findByAnnonceDetailsId(annonceId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double total = 0;
        for (Rating rating : ratings) {
            total += rating.getRatingValue();
        }
        return total / ratings.size();
    }

    public int getRatingCount(int annonceId) {
        List<Rating> ratings = ratingRepository.findByAnnonceDetailsId(annonceId);
        return ratings.size();
    }
}