package com.pmc.market.repository;

import com.pmc.market.entity.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSearchRepository extends JpaRepository<UserSearch, Long> {

    @Query("SELECT us FROM UserSearch us JOIN us.search")
    List<UserSearch> findAll();
}
