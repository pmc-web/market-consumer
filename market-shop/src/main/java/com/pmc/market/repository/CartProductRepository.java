package com.pmc.market.repository;

import com.pmc.market.domain.user.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @Transactional
    @Modifying
    void deleteById(long cartProductId);
}
