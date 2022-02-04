package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.exception.OnlyCanMakeShopOneException;
import com.pmc.market.model.PageRequest;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.model.shop.entity.*;
import com.pmc.market.model.user.entity.Role;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.CategoryRepository;
import com.pmc.market.repository.FavoriteRepository;
import com.pmc.market.repository.ShopImageRepository;
import com.pmc.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;
    private final ShopImageRepository shopImageRepository;
    private final GCSService gcsService;

    @Override
    public List<ShopResponseDto> findAll() {
        return shopRepository.findAll().stream().map(ShopResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public void makeShop(ShopRequestDto shopRequestDto, User user, MultipartFile[] files) {
        // 개인당 1개의 shop 만 생성 가능하도록
        if (!user.getRole().equals(Role.SELLER)) {
            throw new BusinessException("마켓을 생성하려면 판매자로 전환해야 합니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        if (shopRepository.countByUserEmail(user.getEmail()) > 0) {
            throw new OnlyCanMakeShopOneException("계정당 1개의 마켓만 만들 수 있습니다.");
        }
        Category category = categoryRepository.findById(shopRequestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 카테고리가 없습니다."));

        Shop shop = shopRepository.save(shopRequestDto.toEntity(shopRequestDto, user, category));
        // 이미지 업로드
        uploadFiles(files, shop, shopRequestDto.getShopImageType());
    }

    @Override
    public List<ShopResponseDto> findFavorite(PageRequest pageable) {
        return favoriteRepository.findShopMostFavoriteCount(pageable.of())
                .stream().map(shop -> ShopResponseDto.from((Shop) shop[1], (long) shop[0]))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopResponseDto> findNew(PageRequest pageable) {
        return shopRepository.findAll(pageable.of()).stream()
                .map(ShopResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public ShopResponseDto getShopById(long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        return ShopResponseDto.from(shop);
    }

    @Override
    public List<ShopResponseDto> getShopsByCategory(long categoryId) {
        return shopRepository.findByCategory_id(categoryId).stream()
                .map(ShopResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public List<ShopResponseDto> getShopsBySearch(String searchWord) {
        StringBuilder word = new StringBuilder(searchWord);
        word.insert(0, "%");
        word.append("%");
        return shopRepository.findByNameLike(word.toString()).stream()
                .map(ShopResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public void updateShop(ShopRequestDto shopRequestDto, long id, MultipartFile[] files) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));

        if (shop.getCategory().getId() != shopRequestDto.getCategoryId()) {
            Category category = categoryRepository.findById(shopRequestDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 카테고리를 찾을 수 없습니다."));
            shop.updateCategory(category);
        }
        uploadFiles(files, shop, shopRequestDto.getShopImageType());
        shopRequestDto.updateShop(shop);
    }

    @Override
    public void deleteShop(long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        shopRepository.delete(shop); // shop 삭제
    }

    @Override
    public void likeUpdateShop(long shopId, User user) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new EntityNotFoundException("해당 마켓을 찾을 수 없습니다."));
        Optional<Favorite> isFavorite = favoriteRepository.findByShop_IdAndUser_Id(shopId, user.getId());
        if (isFavorite.isPresent()) { // 해제
            Favorite favorite = isFavorite.get();
            favoriteRepository.delete(favorite);
            return;
        }

        Favorite favorite = Favorite.builder()
                .shop(shop)
                .user(user)
                .build();

        favoriteRepository.save(favorite);
        favorite.likeShop();
        shopRepository.save(shop);
    }

    @Transactional
    public void uploadFiles(MultipartFile[] files, Shop shop, ShopImageType shopImageType) {
        List<ShopImage> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                InputStream inputStream = file.getInputStream();
                String path = gcsService.uploadFile(inputStream, file.getOriginalFilename());
                attachments.add(ShopImage.builder()
                        .path(path)
                        .shop(shop)
                        .type(shopImageType)
                        .build());
            } catch (IOException e) {
                throw new BusinessException("파일 업로드중 에러가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        shopImageRepository.saveAll(attachments);
        shop.addImages(attachments);
    }
}
