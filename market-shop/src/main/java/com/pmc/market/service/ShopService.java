package com.pmc.market.service;

import com.pmc.market.entity.Shop;
import com.pmc.market.model.ShopInput;

import java.util.List;

public interface ShopService {

    List<Shop> findAll();
    void makeShop(ShopInput shopInput);
}
