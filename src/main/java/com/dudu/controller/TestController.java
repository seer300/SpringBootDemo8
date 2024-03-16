package com.dudu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/test1")
    public Map<String,Object> test1(){
        Map<String,Object> map = new HashMap<>();
        map.put("value", "1");
        return map;
    }

    @RequestMapping("/test2")
    public Object test2() {
        String sql = "SELECT count(*) FROM `JYT_dbs`.`CaseReview`";
        int i = this.jdbcTemplate.queryForObject(sql, Integer.class);
        return i;
    }

}
