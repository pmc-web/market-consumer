package com.pmc.market.repository;

import com.pmc.market.model.product.entity.ProductFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite, Long> {

    @Query("select f from ProductFavorite f where f.user.id =:userId and f.product.id =:productId")
    Optional<ProductFavorite> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Query("select count (f.product) as likes, f.product from ProductFavorite f group by f.product order by likes desc")
    Page<Object[]> findProductMostFavoriteCount(Pageable pageable);
}
