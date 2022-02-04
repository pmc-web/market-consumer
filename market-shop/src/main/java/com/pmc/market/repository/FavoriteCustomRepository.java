package com.pmc.market.repository;

import com.pmc.market.model.dto.ShopResponseDto;
import com.pmc.market.domain.shop.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FavoriteCustomRepository {

    private final EntityManager entityManager;

    public List<ShopResponseDto> findShopsMostFavoriteCount(int pageNumber, int pageSize) {
        /* custom repository 예제를 위해 남겨둠
            select s.*, f.likes from (SELECT count(*) as likes, shop_id
            FROM market.favorite group by shop_id order by likes desc)f
            inner join shop s on s.id = f.shop_id;
        */
        String sql = "select f.shop, count (s.id) as likes from Favorite f join f.shop s group by s.id order by likes desc";
        List<Object[]> objects = entityManager.createQuery(sql)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize).getResultList();
        return objects.stream()
                .map(result -> ShopResponseDto.from((Shop) result[0], (long) result[1]))
                .collect(Collectors.toList());
    }
}
