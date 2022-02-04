package com.pmc.market.repository;

import com.pmc.market.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    int countByUserEmail(String email);

    @EntityGraph(attributePaths = {"favorites"})
    List<Shop> findAll();

    @EntityGraph(attributePaths = {"favorites"})
    @Query(value = "SELECT s FROM Shop s ORDER BY s.regDate")
    List<Shop> findAllShop();

    @EntityGraph(attributePaths = {"favorites"})
    @Query(value = "SELECT s FROM Shop s ORDER BY s.regDate DESC")
    List<Shop> findAll(Pageable limit);

    @EntityGraph(attributePaths = {"favorites"})
    List<Shop> findByCategory_id(long categoryId);

    @EntityGraph(attributePaths = {"favorites"})
    List<Shop> findByNameLike(String searchWord);
}
