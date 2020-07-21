package com.oktenweb.springmvc.springmoviemvc.service;

import com.oktenweb.springmvc.springmoviemvc.dtos.MovieDTO;
import com.oktenweb.springmvc.springmoviemvc.model.Movie;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MovieService implements IMovieService {


    @Override
    public List<Movie> getAllMovies() {
        String authHeader = "Basic " + Base64.encodeBase64String("myuser:myuser".getBytes());

        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.AUTHORIZATION, authHeader)
                .build();

        Mono<MovieDTO> movieDTOMono = webClient
                .get()
                .uri("/movies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MovieDTO.class);

        MovieDTO movieDTO = movieDTOMono.block();
        return movieDTO.getMovies();

//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8081/movies";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Authorization", authHeader);
//        HttpEntity httpEntity = new HttpEntity(requestHeaders);
//
//        ResponseEntity<MovieDTO> responseEntity =
//                restTemplate.exchange(url, HttpMethod.GET, httpEntity, MovieDTO.class);
//
//        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
//            return responseEntity.getBody().getMovies();
//        } else {
//            throw new RuntimeException("Error retrieving movies");
//        }
    }

    @Override
    public void createMovie(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/movies/1";

        HttpHeaders requestHeaders = new HttpHeaders();
        String authHeader = "Basic " + Base64.encodeBase64String("myuser:myuser".getBytes());
        requestHeaders.add("Authorization", authHeader);

        HttpEntity httpEntity = new HttpEntity(movie, requestHeaders);
        try {
            ResponseEntity<Movie> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST, httpEntity, Movie.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                if (responseEntity.getBody() != null) {
                    return;
                }
            } else {
                throw new RuntimeException("Error creating movie");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
}
