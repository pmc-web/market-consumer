package com.pmc.market.service;

import com.pmc.market.model.entity.Product;
import com.pmc.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void saveProduct(Product product){
        productRepository.save(product);
    }

    @Override
    public Product findOneProduct(Long productId){
        return productRepository.findOne(productId);
    }

    @Override
    public List<Product> findProducts(){
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByKeyword(String keyword) {
        // FIXME
        return productRepository.findAll();
    }

    @Override
    public List<Product> todayPopularProducts() {
        // FIXME
        return productRepository.findAll();
    }
}
