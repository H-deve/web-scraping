package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("SELECT r FROM ratings r WHERE r.annonceDetails.id = :annonceId")
    List<Rating> findByAnnonceDetailsId(@Param("annonceId") int annonceId);

}