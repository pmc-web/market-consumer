package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.model.dto.ShipAddressResponseDto;
import com.pmc.market.model.user.entity.ShipAddress;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.ShipAddressRepository;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
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

    @Override
    public void updateShipAddress(long addressId, ShipAddressRequestDto request) {
        ShipAddress shipAddress = shipAddressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("등록된 주소가 없습니다."));
        shipAddress.updateAddress(request.getAddress(), request.getDetail(), request.getZipCode(), request.getAddressName());
        shipAddressRepository.save(shipAddress);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShipAddressResponseDto> getShipAddressList(long id) {
        return shipAddressRepository.findByUser(id).stream()
                .map(ShipAddressResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteShipAddress(long userId, long addressId) {
        shipAddressRepository.deleteById(addressId);
    }

    @Override
    public boolean setDefaultAddress(long userId, long addressId) {
        // 현재 기본 배송지
        Optional<ShipAddress> isDefault = Optional.ofNullable(shipAddressRepository.findUserDefaultAddress(userId));
        if (isDefault.isEmpty()) throw new BusinessException("기본 배송지를 설정해주세요", ErrorCode.INVALID_INPUT_VALUE);
        if (isDefault.get().getId() == addressId) {
            return false;
        }
        shipAddressRepository.setDefault(isDefault.get().getId(), false);
        shipAddressRepository.setDefault(addressId, true);
        return true;
    }
}
