package com.pmc.market.service;

import com.pmc.market.entity.User;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.dto.ShopResponseDto;

import java.util.List;

public interface ShopService {

    List<ShopResponseDto> findAll();

    void makeShop(ShopRequestDto shopRequestDto, User user);

    List<ShopResponseDto> findFavorite(int pageNumber, int pageSize);

    List<ShopResponseDto> findNew(int pageNumber, int pageSize);

    ShopResponseDto getShopById(long id);

    List<ShopResponseDto> getShopsByCategory(long id);

    List<ShopResponseDto> getShopsBySearch(String searchWord);

    void updateShop(ShopRequestDto shop, long id);

    void deleteShop(long id);

}
