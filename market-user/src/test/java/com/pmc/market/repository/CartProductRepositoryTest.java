package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;


}