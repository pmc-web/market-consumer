package com.pmc.market.entity.dto;

import com.pmc.market.entity.vo.ProductCreateParamVo;
import com.pmc.market.entity.vo.ProductUpdateParamVo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    private String description;

    public ProductDto(ProductCreateParamVo param) {
        this.name = param.getName();
        this.price = param.getPrice();
        this.amount = param.getAmount();
        this.description = param.getDescription();
    }

    public ProductDto(ProductUpdateParamVo param) {
        this.name = param.getName();
        this.price = param.getPrice();
        this.amount = param.getAmount();
        this.description = param.getDescription();
        this.id = param.getId();
    }
}
