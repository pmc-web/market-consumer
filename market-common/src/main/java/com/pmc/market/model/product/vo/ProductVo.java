package com.pmc.market.model.product.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.pmc.market.model.product.entity.Product;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductVo {
    private Long id;
    private String name;
    private Integer price;
    @Nullable
    private Integer amount;
    @Nullable
    private String description;

    @JsonIgnore
    public ProductVo(Product product) {
        this.id = product.getId();
        this.amount = product.getAmount();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.name = product.getName();
    }
}
