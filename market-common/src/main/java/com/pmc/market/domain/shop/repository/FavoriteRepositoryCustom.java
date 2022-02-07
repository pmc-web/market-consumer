package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.shop.dto.ShopResponseDto;
import com.pmc.market.domain.shop.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoriteRepositoryCustom {
    Optional<Favorite> findFavoriteByShopIdAndUserId(long shopId, long userId);
    Page<ShopResponseDto> findShopsMostFavoriteCount(Pageable pageable);
}
