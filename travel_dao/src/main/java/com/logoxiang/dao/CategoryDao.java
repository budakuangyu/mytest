package com.logoxiang.dao;


import com.logoxiang.domain.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryDao {

    @Select("select * from tab_category")
    List<Category> findAll();
    @Select("select * from tab_category where cid = #{cid}")
    Category findOne(int cid);
}
