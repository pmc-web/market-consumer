package com.pmc.market.repository;

import com.pmc.market.model.order.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByPayInfo(String tid);

    @Modifying
    @Transactional
    @Query("update Purchase p set p.payInfo = :tid, p.updateDate = :updateDate where p.id = :id")
    void updateKakaoOrderInfo(@Param("id") long id, @Param("tid") String tid, @Param("updateDate") LocalDateTime now);
}
