package com.pmc.market.repository;

import com.pmc.market.model.product.entity.FavoriteProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductFavoriteRepository extends JpaRepository<FavoriteProduct, Long> {

    @Query("select f from FavoriteProduct f where f.user.id =:userId and f.product.id =:productId")
    Optional<FavoriteProduct> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Query("select count (f.product) as likes, f.product from FavoriteProduct f group by f.product order by likes desc")
    Page<Object[]> findProductMostFavoriteCount(Pageable pageable);
}
