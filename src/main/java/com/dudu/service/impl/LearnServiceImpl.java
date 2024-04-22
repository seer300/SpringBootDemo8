package com.dudu.service.impl;

import com.dudu.dao.newDao;
import com.dudu.domain.LearnResouce;
import com.dudu.service.LearnService;
import com.dudu.tools.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class LearnServiceImpl implements LearnService {

    @Autowired
    newDao newdao;
    @Override
    public int add(LearnResouce learnResouce) {
        return this.newdao.add(learnResouce);
    }

    @Override
    public int update(LearnResouce learnResouce) {
        return this.newdao.update(learnResouce);
    }

    @Override
    public int deleteByIds(String ids) {
        return this.newdao.deleteByIds(ids);
    }

    @Override
    public LearnResouce queryLearnResouceById(Long id) {
        return this.newdao.queryLearnResouceById(id);
    }

    @Override
    public Page queryLearnResouceList(Map<String,Object> params) {
//        return this.newdao.queryLearnResouceList(params);

        List<LearnResouce> list = newdao.queryLearnResouceList(params);
        // Assuming Page constructor or method can accept List and paging parameters
        int total = list.size();  // If you have a different way to get total records, replace this
        int pageNumber = Integer.parseInt(params.get("page").toString());
        int pageSize = Integer.parseInt(params.get("rows").toString());
        return new Page(list, total, pageNumber, pageSize);

    }
    private int getTotalCount(Map<String, Object> params) {
        // 实现获取总记录数的逻辑，比如通过单独的SQL查询
        // 这里仅为示例，需根据实际情况修改
        return newdao.getTotalCount(params);
    }
}
