package com.pmc.market.service;

import com.pmc.market.ProductApplication;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.QnARequestDto;
import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.product.entity.QnAType;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.model.vo.QnAResponseVo;
import com.pmc.market.repository.ProductQnARepository;
import com.pmc.market.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QnAServiceTest {

    @Autowired
    QnAService qnAService;

    @Autowired
    ProductQnARepository productQnARepository;
    @Autowired
    ProductRepository productRepository;

    QnARequestDto request = QnARequestDto.builder()
            .productId(1L)
            .title("배송언제와요")
            .content("배송~~~~~~~~문의합니다.")
            .qnaType(QnAType.DELIVERY)
            .build();

    @DisplayName("큐엔에이 작성")
    @Test
    void makeQnA() {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다."));
        productQnARepository.save(request.toEntity(request, product, User.builder().id(1L).build()));
    }

    @DisplayName("큐엔에이 업데이트")
    @Test
    void update() {
        qnAService.update(3L, request);
    }

    @DisplayName("큐엔에이 단일조회")
    @Test
    void getQnA() {
        QnAResponseVo result = qnAService.getQnA(2L);
        assertThat(result.getQnaId()).isEqualTo(2L);
    }

    @DisplayName("내가쓴 문의글보기")
    @Test
    void 여러개_조회() {
        List<QnAResponseVo> result
//        = qnAService.getUserQnAList();
                = productQnARepository.findByUser(User.builder().id(1L).build()).stream()
                .map(QnAResponseVo::from).collect(Collectors.toList());
        assertThat(result.size()).isGreaterThan(0);
    }

    @DisplayName("내가쓴 문의글보기")
    @Test
    void 여러개_조회2() {
        List<QnAResponseVo> result = qnAService.getProductQnAList(1L);
        assertThat(result.size()).isGreaterThan(0);
    }

    @DisplayName("마켓 문의글보기")
    @Test
    void 여러개_조회3() {
        List<QnAResponseVo> result = qnAService.getShopQnAList(1L);
        assertThat(result.size()).isGreaterThan(0);
    }

    @DisplayName("큐엔에이 삭제")
    @Test
    void getUserQnAList() {
        qnAService.delete(3L);
    }
}