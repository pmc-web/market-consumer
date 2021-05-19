package com.pmc.market.service;

import com.pmc.market.entity.User;
import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.model.dto.ShopRequestDto;

import java.util.List;

public interface ShopService {

    List<ShopResponseDto> findAll();
    void makeShop(ShopRequestDto shopRequestDto, User user);
    List<ShopResponseDto> findFavorite(int count);
    List<ShopResponseDto> findNew(int count);
    ShopResponseDto getShopById(long id);
    List<ShopResponseDto> getShopsByCategory(long id);
}
