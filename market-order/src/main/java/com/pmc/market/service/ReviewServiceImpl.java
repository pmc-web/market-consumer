package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.product.entity.Review;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.ReviewResponseVo;
import com.pmc.market.repository.OrderProductRepository;
import com.pmc.market.repository.OrderRepository;
import com.pmc.market.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void makeReview(ReviewRequestDto reviewRequestDto) {
        Review review = reviewRequestDto.toEntity(reviewRequestDto);
        reviewRepository.save(review);
        orderProductRepository.updateReview(reviewRequestDto.getOrderProductId(), review);
    }

    @Override
    public void updateReview(ReviewRequestDto reviewRequestDto, long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰가 없습니다."));
        reviewRequestDto.updateReview(review);
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseVo> getProductReviews(long productId) {
        List<OrderProduct> orderProducts = orderProductRepository.findByProductId(productId);
        return orderProducts.stream()
                .filter(p -> p.getReview() != null)
                .map(p -> ReviewResponseVo.of(p.getReview()))
                .collect(Collectors.toList());
    }

    @Transactional //lazy
    @Override
    public List<ReviewResponseVo> getShopReviews(long shopId) {
        return orderRepository.findByShopId(shopId)
                .stream().flatMap(order -> order.getProducts().stream())
                .filter(orderProduct -> orderProduct.getReview() != null)
                .map(orderProduct -> ReviewResponseVo.of(orderProduct.getReview()))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseVo getReviewDetail(long reviewId) {
        return ReviewResponseVo.of(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰가 없습니다.")));
    }

    @Transactional // lazy
    @Override
    public List<ReviewResponseVo> getMyReviews(User user) {
        return orderRepository.findByUserOrderByRegDateDesc(user).stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(orderProduct -> orderProduct.getReview() != null)
                .map(orderProduct -> ReviewResponseVo.of(orderProduct.getReview()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(long reviewId) {
        OrderProduct orderProduct = orderProductRepository.findByReviewId(reviewId);
        orderProduct.setReview(null);
        orderProductRepository.save(orderProduct);
        reviewRepository.deleteById(reviewId);
    }
}
