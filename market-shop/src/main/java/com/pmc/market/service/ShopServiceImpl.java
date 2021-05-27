package com.pmc.market.service;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.exception.OnlyCanMakeShopOneException;
import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.dto.NoticeInputDto;
import com.pmc.market.model.dto.ShopDto;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.model.entity.Category;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.entity.ShopNotice;
import com.pmc.market.repository.CategoryRepository;
import com.pmc.market.repository.FavoriteCustomRepository;
import com.pmc.market.repository.NoticeRepository;
import com.pmc.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final FavoriteCustomRepository favoriteCustomRepository;

    private final CategoryRepository categoryRepository;

    private final NoticeRepository noticeRepository;

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public void makeShop(ShopInput shopInput, User user) {
        // 개인당 1개의 shop 만 생성 가능하도록
        if (!user.getRole().equals(Role.SELLER)) {
            throw new BusinessException("마켓을 생성하려면 판매자로 전환해야 합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        if (shopRepository.countByUserEmail(user.getEmail()) > 0) {
            throw new OnlyCanMakeShopOneException("계정당 1개의 마켓만 만들 수 있습니다.");
        }
        shopRepository.save(shopInput.toEntity(shopInput, user));
    }

    @Override
    public List<FavoriteShopDto> findFavorite(int count) {
        return favoriteCustomRepository.findShopsMostFavoriteCount(count);
    }

    @Override
    public List<ShopDto> findNew(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "regDate"));
        Page<Shop> all = shopRepository.findAll(pageable);
        List<Shop> content = all.getContent();
        List<ShopDto> shops = new ArrayList<>();
        content.forEach(shop -> shops.add(ShopDto.of(shop)));
        return shops;
    }

    @Override
    public FavoriteShopDto getShopById(long id) {
        return favoriteCustomRepository.findById(id);
    }

    @Override
    public List<Shop> getShopsByCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new BusinessException("해당하는 카테고리가 없습니다.", ErrorCode.INVALID_INPUT_VALUE));
        return shopRepository.findByCategory(category);
    }

    @Override
    public List<ShopNotice> getNoticeList(long shopId) {
        return noticeRepository.findAllByShopId(shopId);
    }

    @Override
    public ShopNotice insertNotice(long id, NoticeInputDto noticeInputDto) {
        return null;
    }

    @Override
    public ShopNotice getNotice(long noticeId) {
        return null;
    }

    @Override
    public ShopNotice updateNotice(long noticeId, NoticeInputDto noticeInputDto) {
        return null;
    }

    @Override
    public void deleteNotice(long noticeId) {

    }
}
