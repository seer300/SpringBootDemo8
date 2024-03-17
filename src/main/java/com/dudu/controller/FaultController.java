package com.dudu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fault")
public class FaultController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //获取热点案例
    @RequestMapping("/hot")
    public List<Map<String, Object>> getHotFaults(){
        String sql = "SELECT * FROM `JYT_dbs`.`FaultMain` ORDER BY RAND() LIMIT 5";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //获取所有案例数据
    @RequestMapping("/all")
    public List<Map<String, Object>> getAllFaults(){
        String sql = "SELECT * FROM `JYT_dbs`.`FaultMain` LIMIT 100";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //获取个人收藏案例
    @RequestMapping("/star")
    public List<Map<String, Object>> getStarFaults(){
        String sql = "SELECT * FROM `JYT_dbs`.`FaultMain` LIMIT 100";
//        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return null;
    }

}
