package com.pmc.market.repository;

import com.pmc.market.model.dto.FavoriteShopDto;
import com.pmc.market.model.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoriteCustomRepository {

    private final EntityManager entityManager;

    public List<FavoriteShopDto> findShopsMostFavoriteCount(int count){
        /*
        select s.*, f.likes from (SELECT count(*) as likes, shop_id
        FROM market.favorite group by shop_id order by likes desc)f
        inner join shop s on s.id = f.shop_id;
        */
        String sql = "select f.shop, count (s.id) as likes from Favorite f join f.shop s group by s.id order by likes desc";
        List<Object[]> objects = entityManager.createQuery(sql).setMaxResults(count).getResultList();
        List<FavoriteShopDto> result = new ArrayList<>();
        objects.forEach(o->{
            Shop shop = (Shop) o[0];
            long likes = (long) o[1];
            result.add(FavoriteShopDto.of(shop, likes));
        });
        return result;
    }

}
