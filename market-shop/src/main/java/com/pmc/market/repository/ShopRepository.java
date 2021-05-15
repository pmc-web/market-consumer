package com.pmc.market.repository;

import com.pmc.market.model.entity.Category;
import com.pmc.market.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    int countByUserEmail(String email);

    @Query(value = "SELECT s FROM Shop s ORDER BY s.regDate DESC")
    List<Shop> findAll(Pageable limit);

    List<Shop> findByCategory(Category category);
}
