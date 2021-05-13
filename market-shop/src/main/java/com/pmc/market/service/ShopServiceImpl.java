package com.pmc.market.service;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.error.exception.UserNotFoundException;
import com.pmc.market.exception.OnlyCanMakeShopOneException;
import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.repository.FavoriteCustomRepository;
import com.pmc.market.repository.ShopRepository;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final FavoriteCustomRepository favoriteCustomRepository;

    private final UserRepository userRepository;

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public void makeShop(ShopInput shopInput) {
        // 개인당 1개의 shop 만 생성 가능하도록
        User user = userRepository.findByEmail(shopInput.getOwner())
                .orElseThrow(() -> new UserNotFoundException(shopInput.getOwner()));
        if (!user.getRole().equals(Role.SELLER)) {
            throw new BusinessException("마켓을 생성하려면 판매자로 전환해야 합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        if (shopRepository.findByOwner(user.getEmail())) {
            throw new OnlyCanMakeShopOneException("계정당 1개의 마켓만 만들 수 있습니다.");
        }
        shopRepository.save(shopInput.toEntity(shopInput, user));
    }

    @Override
    public List<FavoriteShopDto> findFavorite(int count) {
        return favoriteCustomRepository.findShopsMostFavoriteCount(count);
    }
}
