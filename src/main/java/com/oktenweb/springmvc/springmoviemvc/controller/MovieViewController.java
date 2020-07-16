package com.oktenweb.springmvc.springmoviemvc.controller;

import com.oktenweb.springmvc.springmoviemvc.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieViewController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("movies", movieService.getAllMovies());
        return "movies";
    }
}
