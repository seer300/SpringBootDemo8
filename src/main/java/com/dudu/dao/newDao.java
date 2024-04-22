package com.dudu.dao;
import com.dudu.domain.LearnResouce;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
@Mapper
public interface newDao {

    @Insert("INSERT INTO learn_resource(author, title, url) VALUES(#{author}, #{title}, #{url})")
    int add(LearnResouce learnResouce);

    @Update("UPDATE learn_resource SET author=#{author}, title=#{title}, url=#{url} WHERE id=#{id}")
    int update(LearnResouce learnResouce);

    @Delete("DELETE FROM learn_resource WHERE id IN(${ids})")
    int deleteByIds(@Param("ids") String ids);

    @Select("SELECT * FROM learn_resource WHERE id = #{id}")
    LearnResouce queryLearnResouceById(Long id);

//    List<LearnResouce> queryLearnResouceList(@Param("params") Map<String, Object> params);
    List<LearnResouce> queryLearnResouceList(@Param("params") Map<String, Object> params);

    // 新增：获取总记录数的方法
    @Select({
            "<script>",
            "SELECT COUNT(*) FROM learn_resource",
            "WHERE 1=1",
            "<if test='params.author != null and params.author != \"\"'>",
            "AND author LIKE CONCAT('%', #{params.author}, '%')",
            "</if>",
            "<if test='params.title != null and params.title != \"\"'>",
            "AND title LIKE CONCAT('%', #{params.title}, '%')",
            "</if>",
            "</script>"
    })
    int getTotalCount(@Param("params") Map<String, Object> params);


}