package com.pmc.market.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.error.exception.KakaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuth implements SocialOAuth {

    @Value("${oauth2.social.kakao.base-url:https://kauth.kakao.com/oauth/authorize}")
    private String KAKAO_BASE_URL;

    @Value("${oauth2.social.kakao.client-id:}")
    private String KAKAO_CLIENT_ID;

    @Value("${oauth2.social.kakao.callback-url:http://localhost:8086/login/oauth2/code/kakao}")
    private String KAKAO_CALLBACK_URL;

    @Value("${oauth2.social.kakao.client-secret:}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${oauth2.social.kakao.token-url:https://kauth.kakao.com/oauth/token}")
    private String KAKAO_TOKEN_BASE_URL;

    @Value("${oauth2.social.kakao.user-info-url:https://kapi.kakao.com/v2/user/scopes}")
    private String KAKAO_USER_INFO_URL;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", KAKAO_CLIENT_ID);
        params.put("redirect_uri", KAKAO_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return KAKAO_BASE_URL + "?" + parameterString + "&response_type=code&scope=account_email";
    }

    @Override
    public Map<String, Object> requestAccessToken(String code) {
        try {
            URL url = new URL(KAKAO_TOKEN_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", KAKAO_CLIENT_ID);
            params.put("client_secret", KAKAO_CLIENT_SECRET);
            params.put("redirect_uri", KAKAO_CALLBACK_URL);
            params.put("grant_type", "authorization_code");

            String parameterString = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));

            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
            bous.write(parameterString.getBytes());
            bous.flush();
            bous.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(sb.toString(), Map.class);
                String userId = requestUserKakaoId((String) map.get("access_token"));
                map.put("userId", userId);
                return map;
            }
            throw new KakaoException("Kakao 로그인 요청을 실패했습니다.");
        } catch (IOException e) {
            throw new KakaoException("알 수 없는 Kakao 로그인 Access Token 요청 URL 입니다" + KAKAO_TOKEN_BASE_URL);
        }
    }

    public String requestUserKakaoId(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity("parameters", httpHeaders);
        ResponseEntity response = restTemplate.exchange(URI.create(KAKAO_USER_INFO_URL), HttpMethod.GET, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String result = response.getBody().toString();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Object> map = mapper.readValue(result, Map.class);
                return String.valueOf(map.get("id"));
            } catch (JsonMappingException e) {
                log.error(e.getMessage());
                throw new KakaoException("Kakao 사용자 정보를 가져오던 중 JsonMapping Exception 오류가 발생 했습니다");
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                throw new KakaoException("Kakao 사용자 정보를 가져오던 중 JsonProcessingException 오류가 발생 했습니다");
            }
        }
        throw new KakaoException("Kakao 사용자 정보를 가져오던 중 서버 에러가 발생 했습니다");
    }

}