package com.pmc.market.repository;

import com.pmc.market.model.order.entity.OrderStatus;
import com.pmc.market.model.order.entity.Order;
import com.pmc.market.model.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByPayInfo(String tid);

    @Modifying
    @Transactional
    @Query("update Order p set p.payInfo = :tid, p.updateDate = :updateDate, status = :status where p.id = :id")
    void updateKakaoOrderInfo(@Param("id") long id, @Param("tid") String tid,
                              @Param("updateDate") LocalDateTime now, @Param("status") OrderStatus status);

    @EntityGraph(attributePaths = "products")
    List<Order> findByUserOrderByRegDateDesc(User user);

    @Modifying
    @Transactional
    @Query("update Order p set p.updateDate = :updateDate, status = :status where p.id = :id")
    void updateStatus(@Param("id") long id, @Param("updateDate") LocalDate now, @Param("status") OrderStatus status);

    @Query("select p from Order p where p.shop.id = :shopId order by p.regDate desc")
    List<Order> findByShopId(long shopId);

}
