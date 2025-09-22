package com.example.demo.repository;

import com.example.demo.model.Annonce;
import com.example.demo.model.annonceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsRepository extends JpaRepository<annonceDetails, Integer> {

    List<annonceDetails> findTop6ByOrderByIdDesc();
}
