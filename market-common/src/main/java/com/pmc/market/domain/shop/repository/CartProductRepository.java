package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.user.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @Transactional
    @Modifying
    @Query("delete from CartProduct c where c.id = :id")
    void deleteById(long id);
}
