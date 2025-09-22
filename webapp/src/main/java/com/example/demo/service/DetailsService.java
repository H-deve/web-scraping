package com.example.demo.service;


import com.example.demo.model.annonceDetails;
import com.example.demo.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsService {

    private final DetailsRepository repository;


    @Autowired
    public DetailsService(DetailsRepository repository) {
        this.repository = repository;
    }


    public List<annonceDetails> getDetails(){
        return repository.findAll();
    }
    public List<annonceDetails> getLast6Annonces() {
        return repository.findTop6ByOrderByIdDesc();
    }

    public annonceDetails findById(int annonceId) {
        return repository.findById(annonceId).orElse(null);
    }
}
