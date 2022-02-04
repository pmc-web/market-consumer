package com.pmc.market.service;

import com.pmc.market.model.PageRequest;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService {

    List<ShopResponseDto> findAll();

    void makeShop(ShopRequestDto shopRequestDto, User user, MultipartFile[] files);

    List<ShopResponseDto> findFavorite(PageRequest pageable);

    List<ShopResponseDto> findNew(PageRequest pageable);

    ShopResponseDto getShopById(long id);

    List<ShopResponseDto> getShopsByCategory(long id);

    List<ShopResponseDto> getShopsBySearch(String searchWord);

    void updateShop(ShopRequestDto shop, long id, MultipartFile[] files);

    void deleteShop(long id);

    void likeUpdateShop(long shopId, User user);
}
