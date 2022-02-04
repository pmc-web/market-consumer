package com.pmc.market.repository;

import com.pmc.market.domain.product.entity.ProductQnA;
import com.pmc.market.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductQnARepository extends JpaRepository<ProductQnA, Long> {
    @Transactional
    @Modifying
    void deleteById(long id);

    @EntityGraph(attributePaths = {"product"})
    @Query("select q from ProductQnA q where q.product.id =:productId")
    List<ProductQnA> findByProductId(@Param("productId") long productId);

    @EntityGraph(attributePaths = {"product"})
    @Query("select q from ProductQnA q where q.shop.id =:shopId")
    List<ProductQnA> findByShopId(@Param("shopId") long shopId);

    @EntityGraph(attributePaths = {"product"})
    @Query("select q from ProductQnA q where q.user =:user")
    List<ProductQnA> findByUser(@Param("user") User user);
}
