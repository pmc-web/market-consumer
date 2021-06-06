package com.pmc.market.service;

import com.pmc.market.entity.ShipAddress;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.repository.ShipAddressRepository;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShipAddressServiceImpl implements ShipAddressService {
    private final ShipAddressRepository shipAddressRepository;
    private final UserRepository userRepository;

    @Override
    public void addShipAddress(long userId, ShipAddressRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        ShipAddress shipAddress = request.toEntity(request, user);
        shipAddressRepository.save(shipAddress);
    }
}
