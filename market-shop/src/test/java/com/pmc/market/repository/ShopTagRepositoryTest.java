package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.entity.ShopTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopTagRepositoryTest {

    @Autowired
    private ShopTagRepository shopTagRepository;

    @Test
    void findAll() {
        List<ShopTag> list = shopTagRepository.findAll();

        list.stream().forEach(t -> {
            System.out.println(t.getTag().getName() + " " + t.getShop().getId() + " ");
        });

    }
}