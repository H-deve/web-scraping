package com.example.demo.service;

import com.example.demo.model.Annonce;
import com.example.demo.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnonceService {

    private final AnnonceRepository annonceRepository;


    @Autowired
    public AnnonceService(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    public List<Annonce> getAnnonces() {
        return annonceRepository.findAll();
    }


}
