package com.pmc.market;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ShopApplication {

    @Autowired
    private LogService logService;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @GetMapping("/log")
    public String getLogger() {
        logService.log();
        return "console log";
    }

}
