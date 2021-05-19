package com.pmc.market.repository;

import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.model.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FavoriteCustomRepository {

    private final EntityManager entityManager;

    public List<ShopResponseDto> findShopsMostFavoriteCount(int count) {
        /*
        select s.*, f.likes from (SELECT count(*) as likes, shop_id
        FROM market.favorite group by shop_id order by likes desc)f
        inner join shop s on s.id = f.shop_id;
        */
        String sql = "select f.shop, count (s.id) as likes from Favorite f join f.shop s group by s.id order by likes desc";
        List<Object[]> objects = entityManager.createQuery(sql).setMaxResults(count).getResultList();
        return objects.stream()
                .map(result -> ShopResponseDto.of((Shop) result[0], (long) result[1]))
                .collect(Collectors.toList());
    }

    public FavoriteShopDto findById(long id) {
        // select s, fount(s.id) as likes from favorite f outer join shop s group by s.id; TODO : likes 수가 0 인 경우 찾기
        String sql = "select s, count(f.id) as likes from Shop s left join Favorite.shop f where s.id = :id group by s.id";
        List<Object[]> objects = entityManager.createQuery(sql).setParameter("id", id).getResultList();
        Shop shop = (Shop) objects.get(0)[0];
        long likes = (long) objects.get(0)[1];
        return FavoriteShopDto.of(shop, likes);
    }
}
