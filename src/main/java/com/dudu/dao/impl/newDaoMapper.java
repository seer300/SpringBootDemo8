package com.dudu.dao.impl;

import com.dudu.dao.newDao;
import com.dudu.domain.LearnResouce;
import com.dudu.tools.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class newDaoMapper {
    @Autowired
    private newDao newdao;

    public int add(LearnResouce learnResouce) {
        return newdao.add(learnResouce);
    }

    public int update(LearnResouce learnResouce) {
        return newdao.update(learnResouce);
    }

    public int deleteByIds(String ids) {
        return newdao.deleteByIds(ids);
    }

    public LearnResouce queryLearnResouceById(Long id) {
        return newdao.queryLearnResouceById(id);
    }

    public Page queryLearnResouceList(Map<String, Object> params) {
        List<LearnResouce> list = newdao.queryLearnResouceList(params);
        // Assuming Page constructor or method can accept List and paging parameters
        return new Page(list, Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
    }
}
