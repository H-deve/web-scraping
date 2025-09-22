package com.example.demo.controller;

import com.example.demo.model.Annonce;
import com.example.demo.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AnnonceController {

    private final AnnonceService annonceService;

    @Autowired
    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

//    @RequestMapping("/accueil")
//    public String home(Model model) {
//        List<Annonce> annonces = annonceService.getAnnonces();
//        model.addAttribute("annonces", annonces);
//        return "index";
//    }
//    public ResponseEntity<List<Annonce>> getAnnonces() {
//        List<Annonce> annonces = annonceService.getAnnonces();
//        return ResponseEntity.ok(annonces); // Returns HTTP 200 with the list of annonces
//    }
}
