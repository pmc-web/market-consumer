package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Transactional
    @Modifying
    @Query("delete from Favorite f where f.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    List<Favorite> findByShop_Id(long id);
}
