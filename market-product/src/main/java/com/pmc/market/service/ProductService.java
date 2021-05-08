package com.pmc.market.service;

import com.pmc.market.model.entity.Product;

import java.util.List;

public interface ProductService {

   void saveProduct(Product product);
   Product findOneProduct(Long productId);
   List<Product> findProducts();
}
