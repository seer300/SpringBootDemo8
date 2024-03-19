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
        String sql = "SELECT * FROM `JYT_dbs`.`JYT_Claim_Main` ORDER BY RAND() LIMIT 5";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //获取所有案例数据
    @RequestMapping("/all")
    public List<Map<String, Object>> getAllFaults(){
        String sql = "SELECT * FROM `JYT_dbs`.`JYT_Claim_Main` LIMIT 100";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //获取个人收藏案例
    @RequestMapping("/starlist")
    public List<Map<String, Object>> getStarFaults(){
        String uid = "User1";
        String sql = "SELECT JYT_Claim_Main.*, JYT_Case_collect.F_PKId FROM JYT_Case_collect INNER JOIN JYT_Claim_Main ON JYT_Case_collect.F_PKId = JYT_Claim_Main.F_PKId WHERE JYT_Case_collect.UserID = ?";
        return jdbcTemplate.queryForList(sql,uid);
    }

    //添加个人收藏案例

    //移除个人收藏案例


}
