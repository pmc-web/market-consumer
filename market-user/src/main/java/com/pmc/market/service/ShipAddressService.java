package com.pmc.market.service;

import com.pmc.market.model.dto.ShipAddressRequestDto;

public interface ShipAddressService {
    void addShipAddress(long userId, ShipAddressRequestDto request);
}
