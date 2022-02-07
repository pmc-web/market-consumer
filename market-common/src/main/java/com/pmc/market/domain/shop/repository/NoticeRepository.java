package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.shop.entity.ShopNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<ShopNotice, Long> {
    List<ShopNotice> findAllByShopIdOrderByCreatedDateDesc(long shopId);
}
