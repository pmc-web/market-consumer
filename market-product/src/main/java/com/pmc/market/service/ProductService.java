package com.pmc.market.service;


import com.pmc.market.model.PageRequest;
import com.pmc.market.domain.product.vo.ProductCreateParamVo;
import com.pmc.market.domain.product.vo.ProductUpdateParamVo;
import com.pmc.market.domain.product.vo.ProductVo;
import com.pmc.market.domain.product.vo.SearchProductParam;
import com.pmc.market.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductVo create(ProductCreateParamVo product);

    ProductVo update(ProductUpdateParamVo product);

    ProductVo getById(Long productId);

    Page<ProductVo> get(SearchProductParam searchParam, PageRequest pageable);

    List<ProductVo> getTodayPopularProducts(PageRequest pageable);

    void likeUpdateProduct(Long productId, User user);
}
