package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.dto.ProductRequestDto;
import com.pmc.market.domain.order.entity.OrderProduct;
import com.pmc.market.domain.order.entity.OrderStatus;
import com.pmc.market.domain.order.entity.Pay;
import com.pmc.market.domain.order.entity.Order;
import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.model.vo.OrderResponseVo;
import com.pmc.market.model.vo.kakao.KakaoPayCancelVo;
import com.pmc.market.model.vo.kakao.KakaoPayRequestVo;
import com.pmc.market.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    private final KakaoPayService kakaoPayService;

    public void addOrderProducts(List<ProductRequestDto> requestDtos, Order order) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (ProductRequestDto productDto : requestDtos) {
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다."));
            orderProducts.add(productDto.toEntity(productDto, product, order));
        }
        orderProductRepository.saveAll(orderProducts);
        order.updateProducts(orderProducts);
    }

    @Override
    @Transactional
    public void makeOrder(OrderRequestDto orderRequestDto, User user) {
        Shop shop = shopRepository.findById(orderRequestDto.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 마켓이 없습니다."));
        Order order = orderRequestDto.toEntity(orderRequestDto, shop, user);
        orderRepository.save(order);
        addOrderProducts(orderRequestDto.getProducts(), order); // add products entity
        if (orderRequestDto.getPay().equals(Pay.KAKAO_PAY))
            kakaoPayService.orderKakaoPay(KakaoPayRequestVo.from(order));
        else {
            log.info("IAMPORT 사용 결제"); // TODO : 프론트와 연결
        }
    }

    @Override
    public List<OrderResponseVo> getUserOrderList(User user) {
        return orderRepository.findByUserOrderByRegDateDesc(user).stream()
                .map(OrderResponseVo::from)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseVo getOrder(long orderId) {
        return OrderResponseVo.from(orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 내역이 없습니다.")));
    }

    @Transactional
    @Override
    public void updateState(long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 내역이 없습니다."));
        order.updateStatus(status);
    }

    @Transactional
    @Override
    public String cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 내역이 없습니다."));
        order.cancel();
        if (order.getPay().equals(Pay.KAKAO_PAY)) {
            KakaoPayCancelVo cancelVo = kakaoPayService.cancel(order);
            return cancelVo.getApproved_cancel_amount() + "원 결제 취소되었습니다.";
        }
        return order.getTotalPrice() + "원 결제 취소 되었습니다.";
    }

    @Override
    public void makeOrder(OrderRequestDto request) {
        // TODO
    }


}
