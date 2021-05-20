package com.pmc.market.entity.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.pmc.market.entity.dto.ProductDto;
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
    public ProductVo(ProductDto productDto) {
        this.id = productDto.getId();
        this.amount = productDto.getAmount();
        this.price = productDto.getPrice();
        this.description = productDto.getDescription();
        this.name = productDto.getName();
    }
}
