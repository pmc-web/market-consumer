package com.pmc.market.controller;

import com.pmc.market.security.auth.LoginUser;
import com.pmc.market.security.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

//    private final PostService postService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
//        model.addAttribute("posts", postService.findAllDesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
