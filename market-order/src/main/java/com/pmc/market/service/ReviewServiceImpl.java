package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.product.entity.Review;
import com.pmc.market.model.product.entity.ReviewImage;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.ReviewResponseVo;
import com.pmc.market.repository.OrderProductRepository;
import com.pmc.market.repository.OrderRepository;
import com.pmc.market.repository.ReviewImageRepository;
import com.pmc.market.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final GCSService gcsService;

    @Value("${gcp.bucket:market-universe-storage}")
    private String bucketName;

    @Transactional
    @Override
    public void makeReview(ReviewRequestDto reviewRequestDto, MultipartFile[] multipartFiles) {
        Review review = reviewRequestDto.toEntity(reviewRequestDto);
        reviewRepository.save(review);
        orderProductRepository.updateReview(reviewRequestDto.getOrderProductId(), review);
        uploadFiles(multipartFiles, review);
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

    @Transactional
    public List<ReviewImage> uploadFiles(MultipartFile[] files, Review review) {
        List<ReviewImage> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                InputStream inputStream = file.getInputStream();
                String path = gcsService.uploadFile(inputStream, file.getOriginalFilename());
                attachments.add(ReviewImage.builder().path(path).review(review).build());
            } catch (IOException e) {
                throw new BusinessException("파일 업로드중 에러가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        reviewImageRepository.saveAll(attachments);
        return attachments;
    }

}
