package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.Category;
import com.pmc.market.model.shop.entity.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT s FROM Shop s ORDER BY s.regDate DESC")
    List<Shop> findAll(Pageable limit);

    List<Shop> findByCategory(Category category);

    @Query(value = "SELECT s FROM Shop s WHERE s.name LIKE CONCAT('%', :searchWord, '%') ")
    List<Shop> findByName(@Param("searchWord") String searchWord);

}
