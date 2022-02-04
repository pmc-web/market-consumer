package com.pmc.market.domain.user.repository;

import com.pmc.market.domain.user.entity.ShipAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShipAddressRepository extends JpaRepository<ShipAddress, Long> {

    @Query("SELECT s FROM ShipAddress s JOIN s.user u WHERE u.id = :userId")
    List<ShipAddress> findByUser(@Param("userId") long userId);

    @Transactional
    @Modifying
    @Query("delete from ShipAddress s where s.id = :id")
    void deleteById(@Param("id") long id);

    @Query("SELECT s FROM ShipAddress s JOIN s.user u WHERE u.id = :userId and s.isDefault = true")
    ShipAddress findUserDefaultAddress(@Param("userId") long userId);

    @Transactional
    @Modifying
    @Query("UPDATE ShipAddress s SET s.isDefault = :flag WHERE s.id = :shipId")
    void setDefault(@Param("shipId") long shipId, @Param("flag") boolean flag);

}
