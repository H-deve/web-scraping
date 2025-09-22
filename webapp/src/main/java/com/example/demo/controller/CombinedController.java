package com.example.demo.controller;


import com.example.demo.model.Annonce;
import com.example.demo.model.Rating;
import com.example.demo.model.annonceDetails;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.AnnonceService;
import com.example.demo.service.DetailsService;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class CombinedController {

    private final AnnonceService annonceService;
    private final DetailsService detailsService;
    private final RatingService ratingService;

    @Autowired
    public CombinedController(AnnonceService annonceService, DetailsService detailsService, RatingService ratingService) {
        this.annonceService = annonceService;
        this.detailsService = detailsService;
        this.ratingService = ratingService;
    }

    @RequestMapping("/accueil")
    public String home(Model model) {
        List<Annonce> all = annonceService.getAnnonces();
        List<annonceDetails> details = detailsService.getLast6Annonces();

        // For each annonce, get the average rating and rating count
        for (annonceDetails annonce : details) {
            double averageRating = ratingService.getAverageRating(annonce.getId());
            int ratingCount = (int) ratingService.getAverageRating(annonce.getId());

            int averageRatingFloor = (int) Math.floor(averageRating); //exemple floor 4.7 = 4
            int remainingStars = 5 - averageRatingFloor;

            model.addAttribute("averageRating" + annonce.getId(), averageRating);
            model.addAttribute("averageRatingFloor" + annonce.getId(), averageRatingFloor);
            model.addAttribute("remainingStars" + annonce.getId(), remainingStars);
            model.addAttribute("ratingCount" + annonce.getId(), ratingCount);
        }
//        model.addAttribute("rating".rating) ;
        model.addAttribute("details", details);
        model.addAttribute("all", all);
        return "index";
    }


    @RequestMapping("/submit-rating")
    public String submitRating(@RequestParam int annonceId, @RequestParam int ratingValue) {
        ratingService.submitRating(annonceId, ratingValue);
        return "redirect:/accueil";
    }

    @RequestMapping("/get-rating")
    @ResponseBody
    public double getRating(@RequestParam int annonceId) {

        return ratingService.getAverageRating(annonceId);
    }


    @RequestMapping(path = "/about")
    public String Apropos(Model model) {

        return "Apropos";
    }

    @RequestMapping(path = "/contact")
    public String contact(Model model) {
        List<annonceDetails> details = detailsService.getDetails();
        model.addAttribute("details", details);
        return "contact";
    }

    @RequestMapping(path = "/NosVehicules")
    public String showAllVechucules(Model model) {
        List<Annonce> all = annonceService.getAnnonces();
        List<annonceDetails> details = detailsService.getDetails();
        model.addAttribute("details", details);
        model.addAttribute("all", all);
        return "NosVehicules";
    }
}
