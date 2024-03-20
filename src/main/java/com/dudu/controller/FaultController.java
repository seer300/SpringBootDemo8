package com.dudu.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
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
    @RequestMapping("/addstarlist")
    public int addStarFaults(HttpServletRequest request){
        String uid = "User1";
        //获取需要添加的案例ID
        String fPkId = request.getParameter("F_PKId");
        //首先查询是否已经有了记录
        String sql = "SELECT COUNT(*) FROM JYT_Case_collect WHERE UserID = ? AND F_PKId = ?;";
        Integer i = jdbcTemplate.queryForObject(sql, Integer.class, uid, fPkId);
        if ( i != 0 ){
            return -1;
        }
        //插入记录
        sql = "INSERT INTO JYT_Case_collect (`UserID`, `F_PKId`) VALUES (?, ?)";
        int update = jdbcTemplate.update(sql, uid, fPkId);
        return update;
    }

    //移除个人收藏案例
    @RequestMapping("/delstarcase")
    public int delStarFaults(HttpServletRequest request){
        String uid = "User1";
        //获取需要删除的案例ID
        String fPkId = request.getParameter("F_PKId");
        //循环删除记录
        return jdbcTemplate.update("DELETE FROM `JYT_dbs`.`JYT_Case_collect` WHERE `UserID` = ? AND `F_PKId` = ? LIMIT 1", uid, fPkId);
    }

    //获取指定条件案例-全部
    @RequestMapping("/findAppointFaults")
    public List<Map<String, Object>> findAppointFaults(HttpServletRequest request){
        //获取用户指定的条件
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM JYT_Claim_Main WHERE 1=1");
        List<String> params = new ArrayList<>();

        String carModel = request.getParameter("F_ProModel");
        if (!StringUtils.isEmpty(carModel)) {
            sqlBuilder.append(" AND F_ProModel = ?");
            params.add(carModel);
        }

        String carSeries = request.getParameter("F_ProSerie");
        if (!StringUtils.isEmpty(carSeries)) {
            sqlBuilder.append(" AND F_ProSerie = ?");
            params.add(carSeries);
        }

        String faultKeyword = request.getParameter("F_FaultDescKeyword");
        if (!StringUtils.isEmpty(faultKeyword)) {
            sqlBuilder.append(" AND F_FaultDesc LIKE ?");
            // 将故障关键词添加模糊匹配的百分号
            params.add("%" + faultKeyword + "%");
        }

        String sql = sqlBuilder.toString();
        System.out.println(sql);
        return jdbcTemplate.queryForList(sql, params.toArray());
    }

    //获取指定条件案例-收藏
    @RequestMapping("/findAppointStarFaults")
    public List<Map<String, Object>> findAppointStarFaults(HttpServletRequest request){
        String uid = "User1";
        StringBuilder sqlBuilder = new StringBuilder("SELECT JYT_Claim_Main.* FROM JYT_Case_collect INNER JOIN JYT_Claim_Main ON JYT_Case_collect.F_PKId = JYT_Claim_Main.F_PKId WHERE JYT_Case_collect.UserID = ?");
        List<String> params = new ArrayList<>();

        params.add(uid);

        String carModel = request.getParameter("F_ProModel");
        if (!StringUtils.isEmpty(carModel)) {
            sqlBuilder.append(" AND JYT_Claim_Main.F_ProModel = ?");
            params.add(carModel);
        }

        String carSeries = request.getParameter("F_ProSerie");
        if (!StringUtils.isEmpty(carSeries)) {
            sqlBuilder.append(" AND JYT_Claim_Main.F_ProSerie = ?");
            params.add(carSeries);
        }

        String faultKeyword = request.getParameter("F_FaultDescKeyword");
        if (!StringUtils.isEmpty(faultKeyword)) {
            sqlBuilder.append(" AND JYT_Claim_Main.F_FaultDesc LIKE ?");
            // 将故障关键词添加模糊匹配的百分号
            params.add("%" + faultKeyword + "%");
        }

        String sql = sqlBuilder.toString();
        System.out.println(sql);
        return jdbcTemplate.queryForList(sql, params.toArray());
    }
}
