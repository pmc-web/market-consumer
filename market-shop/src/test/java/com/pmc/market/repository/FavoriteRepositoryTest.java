package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.domain.shop.dto.ShopResponseDto;
import com.pmc.market.domain.shop.entity.Category;
import com.pmc.market.domain.shop.entity.Favorite;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.shop.repository.CategoryRepository;
import com.pmc.market.domain.shop.repository.FavoriteRepository;
import com.pmc.market.domain.shop.repository.ShopRepository;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.domain.user.repository.UserRepository;
import com.pmc.market.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShopRepository shopRepository;

    private User testUser = UserTest.createUser();
    private Category category = Category.builder()
            .mainCategory("main")
            .subCategory("sub")
            .build();
    private Shop savedShop;
    private User savedUser;

    @BeforeEach
    void setup() {
        savedUser = userRepository.save(testUser);
        Category savedCategory = categoryRepository.save(category);
        savedShop = shopRepository.save(ShopTest.createTestShop(savedUser, savedCategory));
    }

    @Test
    void like() {
        Favorite favorite = Favorite.builder()
                .shop(savedShop)
                .user(savedUser)
                .build();

        Favorite savedFavorite = favoriteRepository.save(favorite);
        assertThat(savedFavorite.getShop()).isEqualTo(favorite.getShop());

        User user = userRepository.findById(savedUser.getId()).orElse(null);

        // 연관관계
        favorite.likeShop();
        List<Favorite> favoriteShops = user.getFavoriteShops();
        for (Favorite favoriteShop : favoriteShops) {
            System.out.println("favoriteShop = " + favoriteShop);
        }
        List<Favorite> favorites = shopRepository.findById(savedShop.getId()).get().getFavorites();
        for (Favorite favorite1 : favorites) {
            System.out.println("favorite1 = " + favorite1);
        }
        assertThat(user.getFavoriteShops()).contains(savedFavorite);
        assertThat(savedShop.getFavorites()).contains(savedFavorite);
    }

    @DisplayName("좋아요한 목록 가져온다")
    @Test
    void findFavorite() {
        favoriteRepository.save(Favorite.builder()
                .shop(savedShop)
                .user(savedUser)
                .build());
        Favorite favorite = favoriteRepository.findFavoriteByShopIdAndUserId(savedShop.getId(), savedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("좋아요한 항목이 없습니다."));
        assertThat(favorite).isNotNull();
    }

    @DisplayName("좋아요 순으로 shop 정렬 + 조회 한다.")
    @Test
    void findShopMostFavoriteCount() {
        setting();

        Page<ShopResponseDto> result = favoriteRepository.findShopsMostFavoriteCount(PageRequest.of(0, 10));
        assertThat(result.getContent().get(0).getLikes()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(10);
    }

    private void setting() {
        List<User> users = new ArrayList<>();
        List<Favorite> favorites = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = UserTest.createUser(i);
            users.add(u);
            favorites.add(Favorite.builder().shop(savedShop)
                    .user(u)
                    .build());
        }
        userRepository.saveAll(users);
        favoriteRepository.saveAll(favorites);
        for (Favorite favorite : favorites) {
            favorite.likeShop();
        }
    }
}