package com.pmc.market.service;

import com.pmc.market.model.order.entity.Purchase;
import com.pmc.market.model.vo.kakao.KakaoPayApprovalVo;
import com.pmc.market.model.vo.kakao.KakaoPayCancelVo;
import com.pmc.market.model.vo.kakao.KakaoPayRequestVo;

public interface KakaoPayService {
    void orderKakaoPay(KakaoPayRequestVo request);

    KakaoPayApprovalVo approve(String pgToken);

    KakaoPayCancelVo cancel(Purchase order);

}
