package com.pmc.market.repository;

import com.pmc.market.entity.Search;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("SELECT s FROM Search s ORDER BY s.count DESC")
    List<Search> findBySearchesOrderByCountDesc(Pageable limit);

    @Query("SELECT s.id FROM Search s WHERE s.count<= :count")
    List<Long> findByCount(@Param("count") long count);

    @Transactional
    @Modifying
    @Query("DELETE FROM Search s WHERE s.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
