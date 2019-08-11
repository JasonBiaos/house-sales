package com.soft.house.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: HelloController
 * @Description: TODO
 * @Author: Jason Biao
 * @Date: 2019/6/16 16:44
 * @Version: 1.0
 **/
@Controller
public class HelloController {

    @GetMapping("/index")
    public String index(){
        return "homepage/index";
    }
}
