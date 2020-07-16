package com.oktenweb.springmvc.springmoviemvc.service;

import com.oktenweb.springmvc.springmoviemvc.dtos.MovieDTO;
import com.oktenweb.springmvc.springmoviemvc.model.Movie;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieService implements IMovieService {


    @Override
    public List<Movie> getAllMovies() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/movies";

        HttpHeaders requestHeaders = new HttpHeaders();
        String authHeader = "Basic " + Base64.encodeBase64String("myuser:myuser".getBytes());
        requestHeaders.add("Authorization", authHeader);
        HttpEntity httpEntity = new HttpEntity(requestHeaders);

        ResponseEntity<MovieDTO> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, MovieDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            return responseEntity.getBody().getMovies();
        } else {
            throw new RuntimeException("Error retrieving movies");
        }
    }
}
