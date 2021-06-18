package com.pmc.market.service;

import com.pmc.market.model.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPayServiceImpl implements KakaoPayService {

    @Value("${kakao.pay.ready-url}")
    private String kakaoReadyUrl;

    @Override
    public void orderKakaoPay() {
//        RestTemplate restTemplate = new RestTemplate();
//        String adminKey = "adminKey";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "KakaoAK " + adminKey);
//        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
//
//        // 서버로 요청할 Body
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("cid", "TC0ONETIME");
//        params.add("partner_order_id", "1001");
//        params.add("partner_user_id", "gorany");
//        params.add("item_name", "갤럭시S9");
//        params.add("quantity", "1");
//        params.add("total_amount", "2100");
//        params.add("tax_free_amount", "100");
//        params.add("approval_url", "http://localhost:8080/kakaoPaySuccess");
//        params.add("cancel_url", "http://localhost:8080/kakaoPayCancel");
//        params.add("fail_url", "http://localhost:8080/kakaoPaySuccessFail");
//
//        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//
//        try {
//            KakaoPayReadyVo kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVo.class);
//
//            log.info("" + kakaoPayReadyVO);
//            String next_redirect_pc_url = "";
//            return next_redirect_pc_url;
//
//        } catch (RestClientException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    @Override
    public void requestOrder(OrderRequestDto orderRequestDto) {

    }

    @Override
    public void approve(String pgToken) {

    }

}
