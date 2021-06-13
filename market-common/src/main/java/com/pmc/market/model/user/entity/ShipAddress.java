package com.pmc.market.model.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class ShipAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String detail;

    @NotNull
    private String zipCode;

    private String addressName;

    @Builder.Default
    private Boolean isDefault = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateAddress(String address, String detail, String zipCode, String addressName) {
        this.address = address;
        this.detail = detail;
        this.zipCode = zipCode;
        this.addressName = addressName;
    }
}
