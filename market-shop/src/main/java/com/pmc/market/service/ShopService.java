package com.pmc.market.service;

import com.pmc.market.entity.User;
import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.dto.ShopDto;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopInput;

import java.util.List;

public interface ShopService {

    List<Shop> findAll();
    void makeShop(ShopInput shopInput, User user);
    List<FavoriteShopDto> findFavorite(int count);
    List<ShopDto> findNew(int count);
    FavoriteShopDto getShopById(long id);
    List<ShopDto> getShopsByCategory(long id);
}
