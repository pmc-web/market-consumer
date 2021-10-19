package com.pmc.market.model.product.vo;

import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.product.entity.ProductFavorite;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 김지애(Nova) / jiae.kim413@dreamus.io
 * @since 2021/06/12
 */
@Getter
@AllArgsConstructor
public class ProductLikeRequestParam {
    private String userEmail;
    private Long productId;
}
