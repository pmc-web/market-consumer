package com.pmc.market.service;

import com.pmc.market.model.vo.KakaoPayApprovalVo;
import com.pmc.market.model.vo.KakaoPayCancelVo;
import com.pmc.market.model.vo.KakaoPayRequestVo;

public interface KakaoPayService {
    void orderKakaoPay(KakaoPayRequestVo request);

    KakaoPayApprovalVo approve(String pgToken);

    KakaoPayCancelVo cancel();

}
