package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.exception.KakaoPayException;
import com.pmc.market.domain.order.entity.OrderStatus;
import com.pmc.market.domain.order.entity.Order;
import com.pmc.market.model.vo.kakao.KakaoPayApprovalVo;
import com.pmc.market.model.vo.kakao.KakaoPayCancelVo;
import com.pmc.market.model.vo.kakao.KakaoPayReadyVo;
import com.pmc.market.model.vo.kakao.KakaoPayRequestVo;
import com.pmc.market.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPayServiceImpl implements KakaoPayService {

    private static final String KAKAO_PAY_READY_URL = "/v1/payment/ready";
    private static final String KAKAO_PAY_APPROVE_URL = "/v1/payment/approve";
    private static final String KAKAO_PAY_CANCEL_URL = "/v1/payment/cancel";
    private static final String MOCK_CID = "TC0ONETIME";
    private final OrderRepository orderRepository;
    private final HttpServletResponse response;

    @Value("${kakao.base-url:https://kapi.kakao.com}")
    private String kakaoUrl;
    @Value("${kakao.admin-key}")
    private String adminKey;
    @Value("${app.host}")
    private String host;

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String tid;

    void setRestTemplate() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
    }

    @Override
    public void orderKakaoPay(KakaoPayRequestVo request) {
        setRestTemplate();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("cid", MOCK_CID);
        params.add("partner_order_id", request.getPartnerOrderId());
        params.add("partner_user_id", request.getPartnerUserId());
        params.add("item_name", request.getItemCode());
        params.add("quantity", request.getQuantity());
        params.add("total_amount", request.getTotalAmount());
        params.add("tax_free_amount", request.getTaxFreeAmount());
        params.add("approval_url", host + "/kakaoPay/success");
        params.add("cancel_url", host + "/kakaoPay/cancel");
        params.add("fail_url", host + "/kakaoPay/fail");

        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(params, headers);

        try {
            KakaoPayReadyVo kakaoPayReadyVO = restTemplate
                    .postForObject(new URI(kakaoUrl + KAKAO_PAY_READY_URL), body, KakaoPayReadyVo.class);
            tid = kakaoPayReadyVO.getTid();
            orderRepository.updateKakaoOrderInfo(Long.parseLong(request.getPartnerOrderId()), kakaoPayReadyVO.getTid(), LocalDateTime.now(), OrderStatus.ORDER_CHECKING);

            log.info("KAKAO PAY READY REQUEST VO : {} , SEND REDIRECT", kakaoPayReadyVO);

            response.sendRedirect(kakaoPayReadyVO.getNext_redirect_pc_url());
        } catch (RestClientException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청중 RestClientException 이 발생했습니다.");
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청 중 URISyntaxException 이 발생했습니다.");
        } catch (IOException e) {
            log.error("kakaoReady : sendRedirect Error ", e);
            throw new KakaoPayException("카카오페이 서비스 요청 중 redirect 오류가 발생했습니다.");
        }
    }

    @Override
    public KakaoPayApprovalVo approve(String pgToken) {
        setRestTemplate();
        if (tid == null) throw new BusinessException("카카오페이 결제 tid 가 null 입니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        Order order = orderRepository.findByPayInfo(tid)
                .orElseThrow(() -> new BusinessException("카카오페이 결제 tid 가 잘못되었습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        KakaoPayRequestVo kakaoPayRequest = KakaoPayRequestVo.from(order);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("cid", MOCK_CID);
        params.add("tid", order.getPayInfo());
        params.add("partner_order_id", kakaoPayRequest.getPartnerOrderId());
        params.add("partner_user_id", kakaoPayRequest.getPartnerUserId());
        params.add("pg_token", pgToken);
        params.add("total_amount", kakaoPayRequest.getTotalAmount());
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(params, headers);
        try {
            KakaoPayApprovalVo kakaoPayApprovalVO = restTemplate.postForObject(new URI(kakaoUrl + KAKAO_PAY_APPROVE_URL), body, KakaoPayApprovalVo.class);
            log.info("kakaoPayApprovalVO 결제 승인" + kakaoPayApprovalVO);

            Order purchase = orderRepository.findByPayInfo(kakaoPayApprovalVO.getTid())
                    .orElseThrow(() -> new EntityNotFoundException("구매 이력이 없습니다."));
            orderRepository.updateKakaoOrderInfo(purchase.getId(), kakaoPayApprovalVO.getTid(), LocalDateTime.now(), OrderStatus.PAYMENT_COMPLETE);

            return kakaoPayApprovalVO;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청중 RestClientException 이 발생했습니다.");
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청 중 URISyntaxException 이 발생했습니다.");
        }
    }

    @Override
    public KakaoPayCancelVo cancel(Order order) {
        KakaoPayRequestVo kakaoPayRequest = KakaoPayRequestVo.from(order);

        setRestTemplate();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("cid", MOCK_CID);
        params.add("tid", order.getPayInfo());
        params.add("cancel_amount", kakaoPayRequest.getTotalAmount());
        params.add("cancel_tax_free_amount", kakaoPayRequest.getTaxFreeAmount());

        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(params, headers);
        try {
            KakaoPayCancelVo cancelVo = restTemplate.postForObject(new URI(kakaoUrl + KAKAO_PAY_CANCEL_URL), body, KakaoPayCancelVo.class);
            log.info("KakaoPay cancel" + cancelVo);
            return cancelVo;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청중 RestClientException 이 발생했습니다.");
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new KakaoPayException("카카오페이 서비스 요청 중 URISyntaxException 이 발생했습니다.");
        }
    }

}
