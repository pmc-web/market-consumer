package com.pmc.market.repository;

import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Modifying
    @Transactional
    @Query("update OrderProduct p set p.review = :review where p.id = :id ")
    void updateReview(@Param("id") long id, @Param("review") Review review);


    @Query("select p from OrderProduct p where p.product.id = :productId")
    List<OrderProduct> findByProductId(long productId);

    @Query("select p from OrderProduct p where p.review.id = :reviewId")
    OrderProduct findByReviewId(long reviewId);
}
