package com.pmc.market.model.product.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmc.market.model.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductVo {
    private Long id;
    private String name;
    private Integer price;
    @Nullable
    private Integer amount;
    @Nullable
    private String description;
    private long likes;
    private String size;
    private String color;

    @JsonIgnore
    public ProductVo(Product product) {
        this.id = product.getId();
        this.amount = product.getAmount();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.name = product.getName();
        this.likes = product.getFavoriteProducts().size();
    }

    public static ProductVo of(Product product, long likes) {
        return ProductVo.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .amount(product.getAmount())
                .price(product.getPrice())
                .likes(likes)
                .build();
    }
}
