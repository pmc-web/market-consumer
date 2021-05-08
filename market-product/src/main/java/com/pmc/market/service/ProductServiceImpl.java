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

    public Product findOneProduct(Long productId){
        return productRepository.findOne(productId);
    }

    public List<Product> findProducts(){
        return productRepository.findAll();
    }
}
