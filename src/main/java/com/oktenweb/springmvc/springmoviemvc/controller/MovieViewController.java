package com.oktenweb.springmvc.springmoviemvc.controller;

import com.oktenweb.springmvc.springmoviemvc.model.Movie;
import com.oktenweb.springmvc.springmoviemvc.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieViewController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("newMovie", new Movie());
        return "movies";
    }

    @PostMapping("/movie")
    public String createMovie(@ModelAttribute Movie movie) {
        movieService.createMovie(movie);
        return "redirect:/";
    }
}
