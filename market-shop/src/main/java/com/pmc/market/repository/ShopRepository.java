package com.pmc.market.repository;

import com.pmc.market.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    int countByUserEmail(String email);
}
