package com.pmc.market.repository;

import com.pmc.market.ProductApplication;
import com.pmc.market.domain.product.entity.ProductQnA;
import com.pmc.market.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductQnARepositoryTest {

    @Autowired
    ProductQnARepository productQnARepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 단일_조회() {
        ProductQnA qnA = productQnARepository.findById(1L).get();
        assertThat(qnA.getId()).isEqualTo(1L);
    }

    @Test
    void 상품문의글_리스트_상품기준() {
        List<ProductQnA> qnAS = productQnARepository.findByProductId(1L);

        assertThat(qnAS.size()).isGreaterThan(0);
    }

    @Test
    void 상품문의글_리스트_마켓기준() {
        List<ProductQnA> qnAS = productQnARepository.findByShopId(1L);

        assertThat(qnAS.size()).isGreaterThan(0);
    }

    @Test
    void 상품문의글_리스트_유저기준() {
        User user = userRepository.findById(1L).get();
        List<ProductQnA> qnAS = productQnARepository.findByUser(user);
        assertThat(qnAS.size()).isGreaterThan(0);
    }

    @Test
    void 상품문의_삭제() {
        productQnARepository.deleteById(1);
    }
}