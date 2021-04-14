package com.pmc.market.service;

import com.pmc.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;


}
