package com.pmc.market.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    @Value("${oauth2.social.kakao.user-info-url:https://kauth.kakao.com/v2/user/me}")
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
    public String requestAccessToken(String code) {
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
                log.info(" get token {} -> {}", sb.toString(), map);
                requestUserEmail((String) map.get("access_token"));
                return sb.toString();
            }
            return "카카오 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 카카오 로그인 Access Token 요청 URL 입니다 :: " + KAKAO_TOKEN_BASE_URL);
        }
    }

    public String requestUserEmail(String token) {
        try {
            URL url = new URL(KAKAO_USER_INFO_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer "+ token);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("property_keys", "kakao_account.email");

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
                log.info(" get token {} -> {}", sb.toString(), map);
                requestUserEmail((String) map.get("access_token"));
                return sb.toString();
            }
            return "카카오 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 카카오 로그인 Access Token 요청 URL 입니다 :: " + KAKAO_TOKEN_BASE_URL);
        }
    }
}