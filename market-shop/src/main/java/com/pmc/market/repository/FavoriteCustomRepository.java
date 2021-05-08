package com.pmc.market.repository;
import com.pmc.market.model.FavoriteShopId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FavoriteCustomRepository {

    private final EntityManager entityManager;

    public List<FavoriteShopId> findShopsMostFavoriteCount(Integer count){
        // TODO : FIX Querydsl or Jpql
        String sql = "select s.shop_id from (select count(*) as likes, shop_id " +
                "from favorite group by shop_id order by likes desc limit "+count+") s";
        return entityManager.createNativeQuery(sql).getResultList();
    }

}
