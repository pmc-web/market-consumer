package com.pmc.market.service;


import com.pmc.market.model.PageRequest;
import com.pmc.market.model.product.vo.ProductCreateParamVo;
import com.pmc.market.model.product.vo.ProductUpdateParamVo;
import com.pmc.market.model.product.vo.ProductVo;
import com.pmc.market.model.product.vo.SearchProductParam;
import com.pmc.market.model.user.entity.User;
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
