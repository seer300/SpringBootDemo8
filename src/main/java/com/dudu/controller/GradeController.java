package com.dudu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //获取案例评分
    @RequestMapping("/get")
    public Map getCaseRate(@RequestParam(defaultValue = "User1") String uid, @RequestParam String fPkId){

        String sql = "SELECT * FROM `JYT_dbs`.`JYT_Review_case` WHERE `F_PKId` = ? AND `UserID` = ? LIMIT 1";

        Map<String, Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql, fPkId, uid);
        }catch (Exception e){
            return Collections.emptyMap();
        }
        return map;
    }

    //更新案例评分
    @RequestMapping("/update")
    public int updateCaseRate(@RequestParam(defaultValue = "User1") String uid, @RequestParam String fPkId, @RequestParam String rate){

        String sql;

        // 先检查记录是否存在
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `JYT_dbs`.`JYT_Review_case` WHERE F_PKId = ? AND UserID = ?", Integer.class, fPkId, uid);
        int update;

        if (count > 0) {
            // 更新记录
            update =jdbcTemplate.update("UPDATE `JYT_dbs`.`JYT_Review_case` SET C_Review = ?, C_RTime = ? WHERE F_PKId = ? AND UserID = ?",
                    rate, new Date(), fPkId, uid);
        } else {
            // 插入新记录
            sql = "INSERT INTO `JYT_dbs`.`JYT_Review_case`(`F_PKId`, `UserID`, `C_Review`, `C_RTime`) VALUES (?, ?, ?, ?)";
            update = jdbcTemplate.update(sql, fPkId, uid, rate, new Date());
        }

        return update;
    }

}
