//package com.pmc.market.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(value = "MainController", tags = "메인 컨트롤러")
//@RestController
//@RequestMapping("/common")
//public class MainController {
//
//    // swagger 예제
//    @ApiOperation("메인 ")
//    @GetMapping("/{id}")
//    public String mainTest(@ApiParam(value="id 같은 것") @PathVariable(value = "id")long id){
//        String result = "hi"+String.valueOf(id);
//        return result;
//    }
//}
