package com.dudu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chart")
public class ChartController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //故障车型排行图数据
    @RequestMapping("/carmodel")
    public List<Map<String, Object>> getCarModelChart(){
        String sql = "SELECT F_ProModel, COUNT(*) AS fault_count " +
                "FROM JYT_Claim_Main " +
                "GROUP BY F_ProModel " +
                "ORDER BY fault_count DESC LIMIT 15";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //故障地区排行图数据
    @RequestMapping("/area")
    public List<Map<String, Object>> getAreaChart(){
        String sql = "SELECT F_FaultAdd, COUNT(*) AS fault_count " +
                "FROM JYT_Claim_Main " +
                "GROUP BY F_FaultAdd " +
                "ORDER BY fault_count DESC LIMIT 10";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //故障类型频率表格
    @RequestMapping("/faultType")
    public List<Map<String, Object>> getFaultTypeChart(){
        String sql = "SELECT JYT_Fault_type.F_Category_name, COUNT(JYT_Claim_Main.F_KGFlag) as faultType_count " +
                "FROM JYT_Claim_Main " +
                "JOIN JYT_Fault_type ON JYT_Claim_Main.F_KGFlag = JYT_Fault_type.F_Category_ID " +
                "GROUP BY JYT_Fault_type.F_Category_name LIMIT 10;";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //故障更换部件图表
    @RequestMapping("/faultComponent")
    public List<Map<String, Object>> getFaultComponentChart(){
        String sql = "SELECT JYT_Claim_Main.F_PART, COUNT(*) AS C " +
                "FROM JYT_Claim_Main " +
                "GROUP BY JYT_Claim_Main.F_PART " +
                "ORDER BY C DESC LIMIT 10;";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

}
