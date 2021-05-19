package com.pmc.market.service;

import com.pmc.market.entity.vo.ProductCreateParamVo;
import com.pmc.market.entity.vo.ProductUpdateParamVo;
import com.pmc.market.entity.vo.ProductVo;
import com.pmc.market.entity.vo.SearchProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductVo create(ProductCreateParamVo product);

    ProductVo update(ProductUpdateParamVo product);

    ProductVo getById(Long productId);

    Page<ProductVo> get(SearchProductParam searchParam, Pageable pageable);

    List<ProductVo> getTodayPopularProducts(int limit);
}
