package com.dudu.controller;

import com.dudu.domain.JytClaimMain;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            sqlBuilder.append(" AND F_KGFlag = ?");
            params.add(jytClaimMain.getFKGFlag());
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



        if (!StringUtils.isEmpty(jytClaimMain.getFFaultDesc())) {
            sqlBuilder.append(" AND F_FaultDesc LIKE ?");
            // 将故障关键词添加模糊匹配的百分号
            params.add("%" + jytClaimMain.getFFaultDesc() + "%");
        }


        String sql = sqlBuilder.toString();
        System.out.println(sql);




        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, params.toArray());
        maps.forEach( k ->{

            System.out.println(k);
            int distance = LevenshteinDistance.getDefaultInstance().apply(k.toString(), jytClaimMain.getFFaultDesc());

            // 计算相似度（0-100%）
            double similarity = 100 - (distance * 100.0 / Math.max(k.toString().length(), jytClaimMain.getFFaultDesc().length()));

            k.put("bfb",similarity + "%");
            System.out.println(similarity);
            System.out.println(k);
        });



        return maps;





    }


}