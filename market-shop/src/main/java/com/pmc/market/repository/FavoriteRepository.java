package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select f from Favorite f where f.shop.id =:shopId and f.user.id =:userId")
    Optional<Favorite> findByUserIdAndShopId(long shopId, long userId);

    @Query("select count (f.shop) as likes, f.shop from Favorite f group by f.shop order by likes desc")
    Page<Object[]> findShopMostFavoriteCount(Pageable pageable);
}
