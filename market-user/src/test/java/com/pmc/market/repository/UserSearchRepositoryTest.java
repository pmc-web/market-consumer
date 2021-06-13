package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.model.user.entity.UserSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSearchRepositoryTest {

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Transactional
    @Test
    void findAll() {
        List<UserSearch> objects = userSearchRepository.findAll();
        objects.stream().forEach(o -> {
            System.out.println(o.getId() + " " + o.getSearch().getWord());
        });
    }

}
