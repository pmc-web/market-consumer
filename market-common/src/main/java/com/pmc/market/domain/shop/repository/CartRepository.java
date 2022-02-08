package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.user.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "products")
    List<Cart> findByUser_IdOrderByUpdatedDateDesc(long userId);

    @EntityGraph(attributePaths = "products")
    Optional<Cart> findByUser_IdAndShop_Id(long userId, long shopId);

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.id = :id")
    void deleteById(long id);
}