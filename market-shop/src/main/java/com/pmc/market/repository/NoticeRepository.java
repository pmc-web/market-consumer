package com.pmc.market.repository;

import com.pmc.market.model.shop.entity.ShopNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<ShopNotice, Long> {
    List<ShopNotice> findAllByShopId(long shopId);
}
