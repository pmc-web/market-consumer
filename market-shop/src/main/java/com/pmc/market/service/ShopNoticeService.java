package com.pmc.market.service;

import com.pmc.market.domain.shop.dto.NoticeRequestDto;
import com.pmc.market.domain.shop.dto.NoticeResponseDto;
import com.pmc.market.domain.shop.entity.ShopNotice;

import java.util.List;

public interface ShopNoticeService {
    List<ShopNotice> getNoticeList(long id);

    NoticeResponseDto insertNotice(long id, NoticeRequestDto noticeRequestDto);

    NoticeResponseDto getNotice(long noticeId);

    NoticeResponseDto updateNotice(long noticeId, NoticeRequestDto noticeRequestDto);

    void deleteNotice(long noticeId);
}
