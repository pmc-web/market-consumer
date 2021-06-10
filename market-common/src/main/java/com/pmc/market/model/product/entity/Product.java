package com.pmc.market.model.product.entity;

import com.pmc.market.model.product.vo.ProductCreateParamVo;
import com.pmc.market.model.product.vo.ProductUpdateParamVo;
import com.pmc.market.model.shop.entity.Category;
import com.pmc.market.model.shop.entity.Shop;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Product(ProductCreateParamVo param) {
        this.name = param.getName();
        this.price = param.getPrice();
        this.amount = param.getAmount();
        this.description = param.getDescription();
    }

    public Product(ProductUpdateParamVo param) {
        this.name = param.getName();
        this.price = param.getPrice();
        this.amount = param.getAmount();
        this.description = param.getDescription();
        this.id = param.getId();
    }
}
