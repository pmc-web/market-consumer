package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.dto.ProductRequestDto;
import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.order.entity.Purchase;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.*;
import com.pmc.market.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    @Override
    //    @Transactional TODO:
    public void makeOrder(OrderRequestDto orderRequestDto) {
        Shop shop = shopRepository.findById(orderRequestDto.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 마켓이 없습니다."));
        User user;
        // TODO : SecurityContextHolder 로 찾을지, userId 정보를 받을지
        try {
            user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
//            throw new BusinessException("사용자를 찾지 못했습니다.", ErrorCode.UNAUTHORIZED);
            user = userRepository.findById(orderRequestDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        }
        Purchase purchase = orderRequestDto.toEntity(orderRequestDto, shop, user);
        orderRepository.save(purchase);
        // add products entity
        addOrderProducts(orderRequestDto.getProducts(), purchase);
    }

    public void addOrderProducts(List<ProductRequestDto> requestDtos, Purchase purchase) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (ProductRequestDto productDto : requestDtos) {
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다."));
            orderProducts.add(productDto.toEntity(productDto, product, purchase));
        }
        orderProductRepository.saveAll(orderProducts);
    }
}
