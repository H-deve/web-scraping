package com.example.demo.model;

import jakarta.persistence.*;

@Entity(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rating_value")
    private int rating_value;

    @ManyToOne
    @JoinColumn(name = "annonce_rating_id", nullable = false)
    private annonceDetails annonceDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRatingValue() {
        return rating_value;
    }

    public void setRatingValue(int ratingValue) {
        this.rating_value = ratingValue;
    }

    public annonceDetails getAnnonceDetails() {
        return annonceDetails;
    }

    public void setAnnonceDetails(annonceDetails annonceDetails) {
        this.annonceDetails = annonceDetails;
    }
}
