package com.pmc.market.service;

import com.pmc.market.entity.User;
import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.dto.NoticeInputDto;
import com.pmc.market.model.dto.ShopDto;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.entity.ShopNotice;

import java.util.List;

public interface ShopService {

    List<Shop> findAll();

    void makeShop(ShopInput shopInput, User user);

    List<FavoriteShopDto> findFavorite(int count);

    List<ShopDto> findNew(int count);

    FavoriteShopDto getShopById(long id);

    List<Shop> getShopsByCategory(long id);

    List<ShopNotice> getNoticeList(long id);

    ShopNotice insertNotice(long id, NoticeInputDto noticeInputDto);

    ShopNotice getNotice(long noticeId);

    ShopNotice updateNotice(long noticeId, NoticeInputDto noticeInputDto);

    void deleteNotice(long noticeId);
}
