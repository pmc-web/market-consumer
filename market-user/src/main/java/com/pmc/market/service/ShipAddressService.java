package com.pmc.market.service;

import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.model.dto.ShipAddressResponseDto;

import java.util.List;

public interface ShipAddressService {
    void addShipAddress(long userId, ShipAddressRequestDto request);

    void updateShipAddress(long addressId, ShipAddressRequestDto request);

    List<ShipAddressResponseDto> getShipAddressList(long id);

    void deleteShipAddress(long userId, long addressId);

    boolean setDefaultAddress(long userId, long addressId);
}
