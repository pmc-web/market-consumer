package com.pmc.market.repository;

import com.pmc.market.model.entity.ShopTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopTagRepository extends JpaRepository<ShopTag, Long> {

    @EntityGraph(attributePaths = {"tag"})
    @Query(value = "SELECT t FROM ShopTag t")
    List<ShopTag> findAll();
}
