package com.dudu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test1")
    public Map<String,Object> test1(){
        Map<String,Object> map = new HashMap<>();
        map.put("value", "1");
        return map;
    }

}
