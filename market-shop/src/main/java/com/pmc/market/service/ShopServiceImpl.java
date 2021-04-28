package com.pmc.market.service;

import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public void makeShop(ShopInput shopInput) {
        // TODO : 개인당 1개의 shop 만 생성 가능하도록
//        if (shopRepository.findByOwner("user.getEmail()")) {
//            throw new OnlyCanMakeShopOne("계정당 1개의 마켓만 만들 수 있습니다.");
//        }

        shopRepository.save(Shop.builder()
                .name(shopInput.getName())
                .period(LocalDateTime.now().plusYears(shopInput.getPeriod()))
                .fullDescription(shopInput.getFullDescription())
                .shortDescription(shopInput.getShortDescription())
                .regDate(LocalDateTime.now())
                .businessName(shopInput.getBusinessName())
                .businessNumber(shopInput.getBusinessNumber())
                .owner(shopInput.getOwner())
                .telephone(shopInput.getTelephone())
                .build());
    }
}
