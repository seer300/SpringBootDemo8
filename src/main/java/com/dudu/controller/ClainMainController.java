package com.dudu.controller;

import com.dudu.domain.JytClaimMain;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xm.Similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ClainMain")
public class ClainMainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/getData")
    public List<Map<String, Object>> getData(JytClaimMain jytClaimMain){
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM JYT_Claim_Main WHERE 1=1");
        List<String> params = new ArrayList<>();


        if (!StringUtils.isEmpty(jytClaimMain.getFKGFlag())) {
            //查询该故障系统的对应序号
            String sql1 = "SELECT F_Category_ID " +
                    "FROM JYT_Fault_type " +
                    "WHERE F_Category_name = ?;";

            String fKGFlag = jdbcTemplate.queryForObject(sql1, String.class, jytClaimMain.getFKGFlag());

            sqlBuilder.append(" AND F_KGFlag = ?");
            params.add(fKGFlag);
        }

        if (!StringUtils.isEmpty(jytClaimMain.getFProModel())) {
            sqlBuilder.append(" AND F_ProModel = ?");
            params.add(jytClaimMain.getFProModel());
        }

        if (!StringUtils.isEmpty(jytClaimMain.getFProSerie())) {
            sqlBuilder.append(" AND F_ProSerie = ?");
            params.add(jytClaimMain.getFProSerie());
        }


        if (!StringUtils.isEmpty(jytClaimMain.getMix()) && !StringUtils.isEmpty(jytClaimMain.getMax())) {
            sqlBuilder.append(" AND F_CarMileage between ? and ?");
            params.add(jytClaimMain.getMix());
            params.add(jytClaimMain.getMax());
        }

        String sql = sqlBuilder.toString();
        System.out.println(sql);

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, params.toArray());
        for (Map<String, Object> map : maps) {
            double morphoSimilarityResult = Similarity.morphoSimilarity((String) map.get("F_FaultDesc"), jytClaimMain.getFFaultDesc());
            map.put("bfb", String.format("%.2f", morphoSimilarityResult*100) + "%");
        }

        return maps;
    }


    //获取车系选择列表
    @RequestMapping("/getCarSeries")
    public List<Map<String, Object>> getCarSeries(){
        String sql = "SELECT DISTINCT F_ProSerie FROM JYT_Claim_Main " +
                "WHERE F_ProSerie IS NOT NULL AND F_ProSerie <> '';";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

    //获取品牌选择列表
    @RequestMapping("/getCarModel")
    public List<Map<String, Object>> getCarModel(){
        String sql = "SELECT DISTINCT F_ProModel FROM JYT_Claim_Main " +
                "WHERE F_ProModel IS NOT NULL AND F_ProModel <> '';";

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }
}
