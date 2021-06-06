package com.pmc.market.repository;

import com.pmc.market.entity.ShipAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipAddressRepository extends JpaRepository<ShipAddress, Long> {
}
