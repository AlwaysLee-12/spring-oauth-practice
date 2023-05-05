package com.example.demo.auth;

import com.example.demo.auth.dto.KakaoTokenResponse;
import com.example.demo.common.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final RestTemplateService restTemplateService;
    @Value("${auth.kakao.client.id}")
    private String clientId;
    @Value("${auth.kakao.callback.uri}")
    private String callbackUri;

    public ResponseEntity<String> getUserInfo(String authorizationCode) {
        RestTemplate restTemplate=new RestTemplate();
        String domain = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("client_id", clientId);
        params.add("redirect_uri", callbackUri);
        params.add("code", authorizationCode);
        params.add("grant_type", "authorization_code");

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(domain, request, String.class);
        log.info(String.valueOf(stringResponseEntity));

        return ResponseEntity.ok("a");
    }
}
