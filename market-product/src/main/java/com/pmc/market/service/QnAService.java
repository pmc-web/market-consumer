package com.pmc.market.service;

import com.pmc.market.model.dto.QnARequestDto;
import com.pmc.market.model.dto.QnAResponseVo;

import java.util.List;

public interface QnAService {
    void makeQnA(QnARequestDto qnARequestDto);

    void update(long id, QnARequestDto qnARequestDto);

    QnAResponseVo getQnA(long id);

    List<QnAResponseVo> getUserQnAList();

    List<QnAResponseVo> getProductQnAList(long id);

    List<QnAResponseVo> getShopQnAList(long id);

    void delete(long id);

}
