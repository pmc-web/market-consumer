package com.pmc.market.domain.user.repository;


import com.pmc.market.domain.user.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryCustom {

    @Transactional
    @Modifying
    @Query("DELETE FROM Search s WHERE s.regDate< :date")
    void deleteAllByDate(@Param("date") LocalDateTime date);

    @Query(value = "select count(s.keyword) as count, s.keyword from  Search s where s.reg_date >= :regDate " +
            "group by s.keyword order by count desc limit :limit", nativeQuery = true)
    List<Object[]> findPopularKeyword(@Param("regDate") LocalDateTime date, int limit);

}
