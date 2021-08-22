package com.pmc.market.repository;

import com.pmc.market.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    int updatePassword(String password, Long id);

}
