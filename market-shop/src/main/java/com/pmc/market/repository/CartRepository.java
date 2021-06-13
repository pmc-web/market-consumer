package com.pmc.market.repository;

import com.pmc.market.model.user.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "products")
    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") long userId);

    @EntityGraph(attributePaths = "products")
    @Query("select c from Cart c where c.user.id = :userId and c.shop.id = :shopId")
    Optional<Cart> findByUserIdAndShopId(@Param("userId") long userId, @Param("shopId") long shopId);
}
