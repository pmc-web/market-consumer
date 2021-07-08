package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.QnARequestDto;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.entity.ProductQnA;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.QnAResponseVo;
import com.pmc.market.repository.ProductQnARepository;
import com.pmc.market.repository.ProductRepository;
import com.pmc.market.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QnAServiceImpl implements QnAService {

    private final ProductRepository productRepository;
    private final ProductQnARepository productQnARepository;

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public void makeQnA(QnARequestDto qnARequestDto) {
        Product product = productRepository.findById(qnARequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다."));
        productQnARepository.save(qnARequestDto.toEntity(qnARequestDto, product, getUser()));
    }

    @Override
    public void update(long id, QnARequestDto qnARequestDto) {
        ProductQnA qnA = productQnARepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 문의글이 없습니다."));
        qnARequestDto.updateQnA(qnA);
        productQnARepository.save(qnA);
    }

    @Override
    public QnAResponseVo getQnA(long id) {
        return QnAResponseVo.from(productQnARepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 문의글이 없습니다.")));
    }

    @Transactional
    @Override
    public List<QnAResponseVo> getUserQnAList() {
        return productQnARepository.findByUser(getUser()).stream()
                .map(QnAResponseVo::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<QnAResponseVo> getProductQnAList(long id) {
        return productQnARepository.findByProductId(id).stream()
                .map(QnAResponseVo::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<QnAResponseVo> getShopQnAList(long id) {
        return productQnARepository.findByShopId(id).stream()
                .map(QnAResponseVo::from).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        productQnARepository.deleteById(id);
    }
}
