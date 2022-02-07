package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.shop.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

    @Query("select count (f.shop) as likes, f.shop from Favorite f group by f.shop order by likes desc")
    Page<Object[]> findShopMostFavoriteCount(Pageable pageable);
}
