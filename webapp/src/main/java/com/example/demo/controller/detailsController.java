package com.example.demo.controller;


import com.example.demo.model.Annonce;
import com.example.demo.service.DetailsService;
import com.example.demo.model.annonceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class detailsController {

    private final DetailsService detailsService;

    @Autowired
    public detailsController(DetailsService detailsService) {
        this.detailsService = detailsService;

    }

//    @RequestMapping(path = "/api/details")
//    public String details(Model model) {
//
//        List<annonceDetails> details = detailsService.getDetails();
//        System.out.println(details);
//        model.addAttribute("details", details);
//        return "details";
//    }

//    @RequestMapping(path = "/accueil")
//    public String home(Model model) {
//        List<annonceDetails> details = detailsService.getDetails();
//        List<Annonce> all = new ArrayList<>();
////        System.out.println("Details fetched: " + details); // Debug log
//        model.addAttribute("details", details);
//        model.addAttribute("all", all);
//        return "index";
//    }
//
//    @RequestMapping(path = "/Apropos")
//    public String Apropos(Model model) {
//        List<annonceDetails> details = detailsService.getDetails();
//        model.addAttribute("details", details);
//        return "Apropos";
//    }
//
//    @RequestMapping(path = "/contact")
//    public String contact(Model model) {
//        List<annonceDetails> details = detailsService.getDetails();
//        model.addAttribute("details", details);
//        return "contact";
//    }
//
//    @RequestMapping(path = "/NosVehicules")
//    public String showAllVechucules(Model model) {
//        List<annonceDetails> details = detailsService.getDetails();
//        model.addAttribute("details", details);
//        return "NosVehicules";
//    }

}
