package com.pmc.market.repository;

import com.pmc.market.model.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @EntityGraph(attributePaths = "shopTags")
    @Query(value = "SELECT t FROM Tag t WHERE t.name LIKE CONCAT('%', :searchWord, '%') ")
    List<Tag> findByName(@Param("searchWord") String searchWord);

    @EntityGraph(attributePaths = {"shopTags"})
    List<Tag> findAll();

    @EntityGraph(attributePaths = "shopTags")
    List<Tag> findTagByName(String name);
}
