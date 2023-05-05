package com.example.demo.common.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> clazz, MultiValueMap<String, String> params) {
        return call(url, HttpMethod.GET, headers, null, clazz, params);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, T body, Class<T> clazz, MultiValueMap<String, String> params) {
        return call(url, HttpMethod.POST, headers, body, clazz, params);
    }

    private <T> ResponseEntity<T> call(String url, HttpMethod method, HttpHeaders headers, T body, Class<T> clazz, MultiValueMap<String, String> params) {
        return restTemplate.exchange(url, method, new HttpEntity<>(body, headers), clazz, params);
    }
}
