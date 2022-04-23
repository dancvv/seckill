package com.xxxxx.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/24/1:44 AM
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     *
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "xxxxx");
        return "hello";
    }
}
