package com.practical.task.controllers;

import com.practical.task.configurations.Audit;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JsonPlaceHolderProxyController {

    private static final String JSON_PLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
    private final RestTemplate restTemplate;

    @Audit
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, path = "/posts/**")
    public ResponseEntity<Object> handleGetDeletePostsRequest(HttpServletRequest request) {
        return handleRequest(request, null);
    }

    @Audit
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, path = "/posts/**")
    public ResponseEntity<Object> handlePostPutPostsRequest(HttpServletRequest request, @RequestBody Object body) {
        return handleRequest(request, body);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, path = "/users/**")
    public ResponseEntity<Object> handleGetDeleteUsersRequest(HttpServletRequest request) {
        return handleRequest(request, null);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, path = "/users/**")
    public ResponseEntity<Object> handlePostPutUsersRequest(HttpServletRequest request, @RequestBody Object body) {
        return handleRequest(request, body);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, path = "/albums/**")
    public ResponseEntity<Object> handleGetDeleteAlbumsRequest(HttpServletRequest request) {
        return handleRequest(request, null);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, path = "/albums/**")
    public ResponseEntity<Object> handlePostPutAlbumsRequest(HttpServletRequest request, @RequestBody Object body) {
        return handleRequest(request, body);
    }

    private ResponseEntity<Object> handleRequest(HttpServletRequest request, Object body) {
        String requestUri = request.getRequestURI();
        String url = JSON_PLACEHOLDER_BASE_URL + requestUri.replace("/api", "");
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        ResponseEntity<Object> response = restTemplate.exchange(url, httpMethod, body == null ? HttpEntity.EMPTY : new HttpEntity<>(body) , Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
