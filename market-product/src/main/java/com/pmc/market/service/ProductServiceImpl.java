package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.vo.*;
import com.pmc.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductVo create(ProductCreateParamVo param) {
        Product product = new Product(param);
        return new ProductVo(productRepository.save(product));
    }

    @Override
    public ProductVo getById(Long productId) {
        return new ProductVo(productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상품입니다.")));
    }

    @Override
    public Page<ProductVo> get(SearchProductParam searchParam, Pageable pageable) {
        List<ProductVo> productList = productRepository.findAll(pageable)
                .stream().map(ProductVo::new).collect(Collectors.toList());
        return new PageImpl<>(productList, pageable, productList.size());
    }

    @Override
    public List<ProductVo> getTodayPopularProducts(Pageable pageable) {
        return productRepository.findAll(pageable).stream().map(ProductVo::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductVo update(ProductUpdateParamVo param) {
        Product productDto = new Product(param);
        return new ProductVo(productRepository.save(productDto));
    }
}
