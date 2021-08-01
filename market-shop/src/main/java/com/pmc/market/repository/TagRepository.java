package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @EntityGraph(attributePaths = "shopTags")
    List<Tag> findByNameLike(String searchWord);

    @EntityGraph(attributePaths = {"shopTags"})
    List<Tag> findAll();

    @EntityGraph(attributePaths = "shopTags")
    List<Tag> findTagByName(String name);
}
