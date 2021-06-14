package com.pmc.market.repository;

import com.pmc.market.model.order.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Purchase, Long> {
}
