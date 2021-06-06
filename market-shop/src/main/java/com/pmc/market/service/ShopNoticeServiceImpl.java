package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.NoticeRequestDto;
import com.pmc.market.model.dto.NoticeResponseDto;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.shop.entity.ShopNotice;
import com.pmc.market.repository.NoticeRepository;
import com.pmc.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShopNoticeServiceImpl implements ShopNoticeService {

    private final ShopRepository shopRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public List<ShopNotice> getNoticeList(long shopId) {
        return noticeRepository.findAllByShopId(shopId);
    }

    @Override
    public NoticeResponseDto insertNotice(long id, NoticeRequestDto noticeRequestDto) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        ShopNotice shopNotice = noticeRequestDto.toEntity(noticeRequestDto, shop);
        noticeRepository.save(shopNotice);
        return NoticeResponseDto.from(shopNotice);
    }

    @Override
    public NoticeResponseDto getNotice(long noticeId) {
        ShopNotice shopNotice = noticeRepository.findById(noticeId).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        return NoticeResponseDto.from(shopNotice);
    }

    @Override
    public NoticeResponseDto updateNotice(long noticeId, NoticeRequestDto noticeRequestDto) {
        ShopNotice shopNotice = noticeRepository.findById(noticeId).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        shopNotice.updateNotice(noticeRequestDto);
        noticeRepository.save(shopNotice);
        return NoticeResponseDto.from(shopNotice);
    }

    @Override
    public void deleteNotice(long noticeId) {
        try {
            noticeRepository.deleteById(noticeId);
        } catch (EmptyResultDataAccessException e) {
            // select 쿼리 수를 줄이기 위해 id 조회를 하지 않고 jpa 에서 찾을 수 없다는 exception 발생할 때를 잡는다.
            throw new EntityNotFoundException("해당 마켓을 찾을 수 없습니다.");
        }
    }
}
