package com.pmc.market.service;

import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopInput;

import java.util.List;

public interface ShopService {

    List<Shop> findAll();
    void makeShop(ShopInput shopInput);

    List<FavoriteShopDto> findFavorite(int count);
}
