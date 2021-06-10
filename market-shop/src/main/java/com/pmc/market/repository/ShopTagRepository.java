package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.ShopTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopTagRepository extends JpaRepository<ShopTag, Long> {

    @EntityGraph(attributePaths = {"tag", "shop"})
    List<ShopTag> findAll();

    @EntityGraph(attributePaths = {"tag", "shop"})
    List<ShopTag> findByTagName(String name);

    @EntityGraph(attributePaths = {"tag", "shop"})
    List<ShopTag> findByTagId(long id);
}
