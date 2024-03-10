package com.practical.task.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonPlaceHolderProxyController {

    private static final String JSON_PLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/posts/**")
    public ResponseEntity<Object> getPost(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String url = JSON_PLACEHOLDER_BASE_URL + requestUri.replace("/api", "");
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/posts/**")
    public ResponseEntity<Object> createPost(HttpServletRequest request, @RequestBody Object post) {
        String requestUri = request.getRequestURI();
        String url = JSON_PLACEHOLDER_BASE_URL + requestUri.replace("/api", "");
        HttpEntity<Object> requestEntity = new HttpEntity<>(post);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/posts/**")
    public ResponseEntity<Object> updatePost(HttpServletRequest request, @RequestBody Object post) {
        String requestUri = request.getRequestURI();
        String url = JSON_PLACEHOLDER_BASE_URL + requestUri.replace("/api", "");
        HttpEntity<Object> requestEntity = new HttpEntity<>(post);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/posts/**")
    public ResponseEntity<Object> deletePost(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String url = JSON_PLACEHOLDER_BASE_URL + requestUri.replace("/api", "");
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
