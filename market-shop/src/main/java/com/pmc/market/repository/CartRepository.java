package com.pmc.market.repository;

import com.pmc.market.model.user.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "products")
    List<Cart> findByUser_IdOrderByRegDateDesc(long userId);

    @EntityGraph(attributePaths = "products")
    Optional<Cart> findByUser_IdAndShop_Id(long userId, long shopId);

    @Transactional
    @Modifying
    void deleteById(long cartId);
}
