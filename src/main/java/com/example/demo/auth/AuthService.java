package com.example.demo.auth;

import com.example.demo.auth.dto.KakaoTokenResponse;
import com.example.demo.auth.dto.KakaoUserResponse;
import com.example.demo.common.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final RestTemplateService restTemplateService;
    @Value("${auth.kakao.client.id}")
    private String clientId;
    @Value("${auth.kakao.callback.uri}")
    private String callbackUri;

    public String getUserInfo(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = this.getToken(restTemplate, authorizationCode);
        String domain = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.add("Accept", "application/json");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponse> kakaoResponse = restTemplate.postForEntity(domain, request, KakaoUserResponse.class);
        log.info(String.valueOf(kakaoResponse.getBody().getId()));

        return kakaoResponse.getBody().getId();
    }

    private String getToken(RestTemplate restTemplate, String authorizationCode) {
        String domain = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("client_id", clientId);
        params.add("redirect_uri", callbackUri);
        params.add("code", authorizationCode);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> kakaoResponse = restTemplate.postForEntity(domain, request, KakaoTokenResponse.class);
        log.info(String.valueOf(kakaoResponse.getBody().getAccess_token()));

        return kakaoResponse.getBody().getAccess_token();
    }
}
