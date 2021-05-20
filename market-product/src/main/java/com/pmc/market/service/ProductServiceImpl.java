package com.pmc.market.service;

import com.pmc.market.entity.vo.ProductCreateParamVo;
import com.pmc.market.entity.vo.ProductUpdateParamVo;
import com.pmc.market.entity.vo.ProductVo;
import com.pmc.market.entity.vo.SearchProductParam;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.entity.dto.ProductDto;
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
        ProductDto productDto = new ProductDto(param);
        return new ProductVo(productRepository.save(productDto));
    }

    @Override
    public ProductVo getById(Long productId) {
        return new ProductVo(productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("존재하지 않는 상품입니다.", ErrorCode.INVALID_INPUT_VALUE)));
    }

    @Override
    public Page<ProductVo> get(SearchProductParam searchParam, Pageable pageable) {
        List<ProductVo> productList = productRepository.findAll()
                .stream().map(ProductVo::new).collect(Collectors.toList());
        return new PageImpl<>(productList, pageable, productList.size());
    }

    @Override
    public List<ProductVo> getTodayPopularProducts(int limit) {
        // FIXME
        return productRepository.findAll().stream().map(ProductVo::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductVo update(ProductUpdateParamVo param) {
        ProductDto productDto = new ProductDto(param);
        return new ProductVo(productRepository.save(productDto));
    }
}
