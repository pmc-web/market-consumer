package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.PageRequest;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.entity.ProductFavorite;
import com.pmc.market.model.product.vo.ProductCreateParamVo;
import com.pmc.market.model.product.vo.ProductUpdateParamVo;
import com.pmc.market.model.product.vo.ProductVo;
import com.pmc.market.model.product.vo.SearchProductParam;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.ProductFavoriteRepository;
import com.pmc.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductFavoriteRepository productFavoriteRepository;

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
    public Page<ProductVo> get(SearchProductParam searchParam, PageRequest pageable) {
        List<ProductVo> productList = productRepository.findAll(pageable.of())
                .stream().map(ProductVo::new).collect(Collectors.toList());
        return new PageImpl<>(productList, pageable.of(), productList.size());
    }

    @Override
    public List<ProductVo> getTodayPopularProducts(PageRequest pageable) {
        return productFavoriteRepository.findProductMostFavoriteCount(pageable.of()).getContent().stream()
                .map(product -> ProductVo.of((Product) product[1], (long) product[0]))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void likeUpdateProduct(Long productId, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
        Optional<ProductFavorite> isFavorite = productFavoriteRepository.findByUserIdAndProductId(user.getId(), productId);
        if (isFavorite.isPresent()) {
            productFavoriteRepository.delete(isFavorite.get());
            return;
        }
        ProductFavorite favorite = ProductFavorite.builder()
                .product(product)
                .user(user)
                .build();
        productFavoriteRepository.save(favorite);

    }

    @Override
    @Transactional
    public ProductVo update(ProductUpdateParamVo param) {
        Product productDto = new Product(param);
        return new ProductVo(productRepository.save(productDto));
    }
}
