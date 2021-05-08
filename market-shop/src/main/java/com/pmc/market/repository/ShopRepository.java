package com.pmc.market.repository;

import com.pmc.market.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    boolean findByOwner(String owner);

//    List<Shop> findAllById(List<String> shopIds);
}
